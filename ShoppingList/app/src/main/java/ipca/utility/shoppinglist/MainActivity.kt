package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    // model
    val productList = arrayListOf<Product>(
        Product("Apples", 2),
        Product("Oranges", 4),
        Product("Strawberries", 20),
    )

    val productAdapter = ProductListAdapter()

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            data?.let {
                val qtd = it.extras?.getInt(ProductDetailActivity.DATA_QTD)
                val productName = it.extras?.getString(ProductDetailActivity.DATA_NAME)
                println("Product:${qtd} ${productName}")
                val newProduct = Product(productName,qtd?:0)
                productList.add(newProduct)
                productAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = productAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, ProductDetailActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    inner class ProductListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return productList.size
        }

        override fun getItem(position: Int): Any {
            return productList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_product,parent,false)
            val textViewProduct = rootView.findViewById<TextView>(R.id.textViewProductName)
            val textViewQty = rootView.findViewById<TextView>(R.id.textViewQty)
            val checkBox = rootView.findViewById<CheckBox>(R.id.checkBox)
            textViewProduct.text = productList[position].name
            textViewQty.text = "QTD: ${productList[position].qtd}"
            checkBox.isChecked = productList[position].isChecked

            rootView.setOnClickListener{
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.DATA_QTD, productList[position].qtd)
                intent.putExtra(ProductDetailActivity.DATA_NAME, productList[position].name)
                resultLauncher.launch(intent)
            }

            return rootView
        }

    }
}