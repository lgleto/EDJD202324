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
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // model
    var productList : List<Product> = arrayListOf<Product>()

    val productAdapter = ProductListAdapter()

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            data?.let {
                val qtd = it.extras?.getInt(ProductDetailActivity.DATA_QTD)
                val productName = it.extras?.getString(ProductDetailActivity.DATA_NAME)
                val position = it.extras?.getInt(ProductDetailActivity.DATA_POSITION)?:-1
                println("Product:${qtd} ${productName}")
                if (position == -1) {
                    val newProduct = Product(
                        System.currentTimeMillis(),
                        productName,
                        qtd ?: 0)

                    GlobalScope.launch (Dispatchers.IO) {
                        AppDatabase.getDatabase(applicationContext)?.productDao()?.insert(newProduct)
                    }



                }else{
                    productList[position].name = productName
                    productList[position].qtd = qtd?:0

                    GlobalScope.launch (Dispatchers.IO) {
                        AppDatabase.getDatabase(applicationContext)?.productDao()
                            ?.update(productList[position])
                    }

                }

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

        AppDatabase.getDatabase(applicationContext)?.
            productDao()?.
            getAll()?.
            observe(this, Observer {
                productList = it
                productAdapter.notifyDataSetChanged()
            })

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

            checkBox.setOnClickListener {
                productList[position].isChecked = checkBox.isChecked

                GlobalScope.launch (Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext)?.productDao()
                        ?.update(productList[position])
                }
            }

            rootView.setOnClickListener{
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.DATA_QTD, productList[position].qtd)
                intent.putExtra(ProductDetailActivity.DATA_NAME, productList[position].name)
                intent.putExtra(ProductDetailActivity.DATA_POSITION, position)
                resultLauncher.launch(intent)
            }

            return rootView
        }

    }
}