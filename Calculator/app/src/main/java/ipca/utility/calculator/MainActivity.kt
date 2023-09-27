package ipca.utility.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var textViewDisplay : TextView? = null

    var operation : String? = null
    var operand : Double = 0.0

    var onOperationPressed : (View)->Unit = {

        operation?.let {
            doOperation()
        }

        operation = (it as Button).text.toString()
        operand = textViewDisplay?.text.toString().toDouble()
        userIsInTheMiddleOfIntroduction = false

    }

    var onDigitPressed : (View)->Unit = {
        val num = (it as Button).text.toString()
        val display = textViewDisplay?.text.toString()
        if (userIsInTheMiddleOfIntroduction){
            if (num == "."){
                if(!display.contains("."))
                    textViewDisplay?.text = display + num
            }else{
                if (display == "0") {
                    textViewDisplay?.text = num
                }else{
                    textViewDisplay?.text = display + num
                }
            }
        }else{
            textViewDisplay?.text = num
        }
        userIsInTheMiddleOfIntroduction = true
    }

    var userIsInTheMiddleOfIntroduction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        val button0   = findViewById<Button>(R.id.button0)
        val button1   = findViewById<Button>(R.id.button1)
        val button2   = findViewById<Button>(R.id.button2)
        val button3   = findViewById<Button>(R.id.button3)
        val button4   = findViewById<Button>(R.id.button4)
        val button5   = findViewById<Button>(R.id.button5)
        val button6   = findViewById<Button>(R.id.button6)
        val button7   = findViewById<Button>(R.id.button7)
        val button8   = findViewById<Button>(R.id.button8)
        val button9   = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        val buttonAdd      = findViewById<Button>(R.id.buttonAdd     )
        val buttonMinus    = findViewById<Button>(R.id.buttonMinus   )
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide   = findViewById<Button>(R.id.buttonDivide  )

        val buttonEqual   = findViewById<Button>(R.id.buttonEqual  )

        button0.setOnClickListener(onDigitPressed)
        button1.setOnClickListener(onDigitPressed)
        button2.setOnClickListener(onDigitPressed)
        button3.setOnClickListener(onDigitPressed)
        button4.setOnClickListener(onDigitPressed)
        button5.setOnClickListener(onDigitPressed)
        button6.setOnClickListener(onDigitPressed)
        button7.setOnClickListener(onDigitPressed)
        button8.setOnClickListener(onDigitPressed)
        button9.setOnClickListener(onDigitPressed)
        buttonDot.setOnClickListener(onDigitPressed)

        buttonAdd.setOnClickListener(onOperationPressed)
        buttonMinus.setOnClickListener(onOperationPressed)
        buttonMultiply.setOnClickListener(onOperationPressed)
        buttonDivide.setOnClickListener(onOperationPressed)

        buttonEqual.setOnClickListener {
            doOperation()
            userIsInTheMiddleOfIntroduction = false
        }
    }

    fun doOperation () {
        val currentNum = textViewDisplay?.text.toString().toDouble()
        val result = when (operation) {
            "+" -> operand  + currentNum
            "-" -> operand  - currentNum
            "*" -> operand  * currentNum
            "/" -> operand  / currentNum
            else -> 0.0
        }
        var displayText = ""
        if (result - result.toInt() == 0.0) {
            displayText = result.toInt().toString()
        }else{
            displayText = result.toString()
        }
        textViewDisplay?.setText(displayText)
    }

}