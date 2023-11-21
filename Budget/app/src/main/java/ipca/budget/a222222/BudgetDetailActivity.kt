package ipca.budget.a222222

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class BudgetDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_detail)

        val description = findViewById<EditText>(R.id.editTextDescription)
        val value = findViewById<EditText>(R.id.editTextValue)

        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent()
            intent.putExtra( "description", description.text.toString() )
            intent.putExtra( "value", value.text.toString().toDouble() )
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}