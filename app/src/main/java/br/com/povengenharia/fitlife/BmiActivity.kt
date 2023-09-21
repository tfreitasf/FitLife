package br.com.povengenharia.fitlife

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.povengenharia.fitlife.model.Calc

class BmiActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.et_bmi_weight)
        editHeight = findViewById(R.id.et_bmi_height)


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

            val dialog = AlertDialog.Builder(this)

                .setTitle(getString(R.string.imc_response, bmiResult))
                .setMessage(bmiResultRange)
                .setPositiveButton(android.R.string.ok) { p0, p1 -> }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = bmiResult))

                        runOnUiThread {
                            Toast.makeText(this@BmiActivity, R.string.saved, Toast.LENGTH_LONG)
                                .show()
                        }


                    }.start()

                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


        }


    }

    @StringRes
    private fun bmiResponde(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.imc_normal
            imc < 30.0 -> R.string.imc_hight_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
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