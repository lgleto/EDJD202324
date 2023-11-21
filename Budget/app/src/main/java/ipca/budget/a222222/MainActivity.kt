package ipca.budget.a222222

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.Date

class MainActivity : AppCompatActivity() {

    var budgetItems = arrayListOf<BudgetItem>(
        BudgetItem("Peras", 10.0, Date()),
        BudgetItem("Maças", 7.0, Date()),
        BudgetItem("laranjas", 2.5, Date()),
    )

    val adapter = BudgetAdapter()

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts
            .StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let {intent ->
                    val description = intent.getStringExtra("description")!!
                    val value = intent.getDoubleExtra("value",0.0)
                    val budgetItem = BudgetItem(description,value , Date())
                    budgetItems.add(budgetItem)
                    adapter.notifyDataSetChanged()
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listViewItems)
        listView.adapter = adapter

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this@MainActivity, BudgetDetailActivity::class.java)
            resultLauncher.launch(intent)
        }

        findViewById<Button>(R.id.buttonSort).setOnClickListener {
            budgetItems = ArrayList(budgetItems.sortedBy { it.description })
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.buttonTotal).setOnClickListener {
            var result = 0.0
            for (b in budgetItems){
                result += b.value
            }
            Toast.makeText(this@MainActivity, "Total:$result", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.action_sort -> {
                budgetItems = ArrayList(budgetItems.sortedBy { it.description })
                adapter.notifyDataSetChanged()
                true
            }
            R.id.action_total -> {
                var result = 0.0
                for (b in budgetItems){
                    result += b.value
                }
                Toast.makeText(this@MainActivity, "Total:$result", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class BudgetAdapter : BaseAdapter() {
        override fun getCount(): Int {
           return budgetItems.count()
        }

        override fun getItem(position: Int): Any {
            return budgetItems[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_item,parent,false)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewValue = rootView.findViewById<TextView>(R.id.textViewValue)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)

            textViewDescription.text = budgetItems[position].description
            textViewValue.text = budgetItems[position].value.toString()
            textViewDate.text = budgetItems[position].date.toString()

            return rootView
        }

    }
}