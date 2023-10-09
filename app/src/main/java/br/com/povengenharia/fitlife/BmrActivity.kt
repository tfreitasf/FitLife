package br.com.povengenharia.fitlife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.povengenharia.fitlife.model.Calc

enum class Gender { MAN, WOMAN }

class BmrActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView

    private lateinit var radioGroup: RadioGroup
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    private var selectedGender: Gender? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tbm_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        radioGroup = findViewById(R.id.rgroup_gender)
        editWeight = findViewById(R.id.et_bmr_weight)
        editHeight = findViewById(R.id.et_bmr_height)
        editAge = findViewById(R.id.et_bmr_age)




        val buttonCalculate: Button = findViewById(R.id.btn_bmr_calculate)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedGender = when (checkedId) {
                R.id.rb_gender_man -> Gender.MAN
                R.id.rb_gender_woman -> Gender.WOMAN
                else -> null

            }
        }

        buttonCalculate.setOnClickListener {
            val exerciseIndex = items.indexOf(lifestyle.text.toString())

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
            val age = editAge.text.toString().toInt()

            val bmrResult = calculateBaseBrm(weight, height, age, selectedGender, exerciseIndex)

            val dialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.bmr_response, bmrResult))
                .setMessage(getString(R.string.bmr_msg))
                .setPositiveButton(android.R.string.ok) { p0, p1 -> }
                .setNegativeButton(R.string.save){_,_ ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "bmr", res = bmrResult))

                        runOnUiThread {
                            openBmrHistory()

                        }

                    }.start()
                }

                .create()
                .show()
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


        }


    }

    private fun calculateBaseBrm(
        weight: Double,
        height: Int,
        age: Int,
        gender: Gender?,
        exerciseIndex: Int
    ): Double {
        val exerciseMultipliers = arrayOf(1.2, 1.375, 1.55, 1.725, 1.9)

        return when (gender) {
            Gender.MAN -> exerciseMultipliers[exerciseIndex] *  (66 + ((13.7 * weight) + (5 * height) - (6.8 * age)))
            Gender.WOMAN -> exerciseMultipliers[exerciseIndex] * (655 + ((9.6 * weight) + (1.8 * height) - (4.7 * age)))
            null -> 0.0

        }
    }

    private fun openBmrHistory(){
        val intent = Intent(this@BmrActivity, BmrHistoryActivity::class.java)
        intent.putExtra("type", "bmr") // Use "type" como a chave
        startActivity(intent)
    }


    private fun validate(): Boolean {
        return (selectedGender != null
                && editHeight.text.toString().isNotEmpty()
                && editWeight.text.toString().isNotEmpty()
                && editAge.text.toString().isNotEmpty()
                && !editHeight.text.toString().startsWith("0")
                && !editWeight.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0")
                )
    }
}