package ipca.budget.customcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jdgTop = findViewById<JogoDoGaloView>(R.id.jogoDoGaloViewTop)
        val textViewCount = findViewById<TextView>(R.id.textViewCount)
        jdgTop.setOnClickPointListener {
            textViewCount.text = it.toString()
            jdgTop.isBlocked = true
        }

    }
}