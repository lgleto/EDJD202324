package ipca.utility.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var calculatorBrain = CalculatorBrain()

    var textViewDisplay : TextView? = null
    var userIsInTheMiddleOfIntroduction = false

    var currentDisplay : Double
        get() = textViewDisplay?.text.toString().toDouble()
        set(value) {
            var displayText = ""
            if (value - value.toInt() == 0.0) {
                displayText = value.toInt().toString()
            }else{
                displayText = value.toString()
            }
            textViewDisplay?.setText(displayText)
        }

    var onOperationPressed : (View)->Unit = {
        calculatorBrain.operation?.let {
            currentDisplay = calculatorBrain.doOperation(currentDisplay)
        }

        calculatorBrain.operation = when ((it as Button).text.toString()) {
            "+" -> CalculatorBrain.Operation.SUM
            "-" -> CalculatorBrain.Operation.SUBTRACTION
            "*" -> CalculatorBrain.Operation.MULTIPLICATION
            "/" -> CalculatorBrain.Operation.DIVISION
            else -> null
        }
        calculatorBrain.accumulator = textViewDisplay?.text.toString().toDouble()
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
        val buttonAC      = findViewById<Button>(R.id.buttonAC     )

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
            currentDisplay = calculatorBrain.doOperation(currentDisplay)
            userIsInTheMiddleOfIntroduction = false
        }

        buttonAC.setOnClickListener {
            textViewDisplay?.text = "0"
            userIsInTheMiddleOfIntroduction = false
            calculatorBrain.accumulator = 0.0
            calculatorBrain.operation = null
        }
    }



}