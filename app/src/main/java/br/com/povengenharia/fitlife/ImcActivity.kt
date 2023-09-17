package br.com.povengenharia.fitlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var textBmi: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.et_bmi_weight)
        editHeight = findViewById(R.id.et_bmi_height)
        textBmi = findViewById(R.id.tv_bmi_result)

        val buttonCalculate: Button = findViewById(R.id.btn_bmi_calculate)

        buttonCalculate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_message_validation),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }
            val weight = editWeight.text.toString().toDouble()
            val height = editHeight.text.toString().toInt()

            val bmiResult = calculateBmi(weight, height)

            val bmiResultRange = bmiResponde(bmiResult)
            textBmi.text = getString(bmiResultRange)
            Toast.makeText(this,bmiResultRange, Toast.LENGTH_SHORT).show()





        }


    }

    private fun bmiResponde(imc: Double): Int {
        if (imc < 15.0) {
            return R.string.imc_severely_low_weight
        } else if (imc < 16.0) {
            return R.string.imc_very_low_weight
        } else if (imc < 18.5) {
            return R.string.imc_low_weight
        } else if (imc < 25.0) {
            return R.string.imc_normal
        } else if (imc < 30.0){
            return R.string.imc_hight_weight
        } else if (imc < 35.0){
            return R.string.imc_so_high_weight
        } else if (imc < 40){
            return R.string.imc_severely_high_weight
        } else{
            return R.string.imc_extreme_weight
        }
    }

    private fun validate(): Boolean {
        return (editHeight.text.toString().isNotEmpty()
                && editWeight.text.toString().isNotEmpty()
                && !editHeight.text.toString().startsWith("0")
                && !editWeight.text.toString().startsWith("0"))

    }

    private fun calculateBmi(weight: Double, height: Int): Double {
        val heightInMeters = height / 100.0
        return weight / (heightInMeters * heightInMeters)

    }


}