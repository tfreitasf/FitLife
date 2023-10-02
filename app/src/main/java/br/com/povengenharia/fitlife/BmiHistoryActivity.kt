package br.com.povengenharia.fitlife

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.povengenharia.fitlife.model.Calc
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BmiHistoryActivity : AppCompatActivity() {

    private lateinit var rvBmiHistory: RecyclerView
    private val bmiHistoryList = mutableListOf<Calc>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)


        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            bmiHistoryList.clear()
            bmiHistoryList.addAll(response)




            runOnUiThread {
                //Log.i("Teste", "Resposta: $response")
                val adapter = BmiHistoryAdapter(bmiHistoryList)
                rvBmiHistory = findViewById(R.id.rv_bmi_history)
                rvBmiHistory.adapter = adapter
                rvBmiHistory.layoutManager = LinearLayoutManager(this)


            }


        }.start()


    }

    private inner class BmiHistoryAdapter(
        private val bmiList: List<Calc>
    ) :
        RecyclerView.Adapter<BmiHistoryHolder>(

        ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmiHistoryHolder {
            val view = layoutInflater.inflate(R.layout.bmi_history_item, parent, false)
            return BmiHistoryHolder(view)


        }

        override fun onBindViewHolder(holder: BmiHistoryHolder, position: Int) {
            val calc = bmiHistoryList[position]
            holder.bind(calc)


        }

        override fun getItemCount(): Int {
            return bmiList.size

        }
    }


    private inner class BmiHistoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(calc: Calc) {


            val textViewResult = itemView.findViewById<TextView>(R.id.tv_result_calc)
            val textViewDate = itemView.findViewById<TextView>(R.id.tv_date)

            val formattedDate = formatDate(calc.createdDate)
            val formatResult = String.format("%.2f", calc.res)



            textViewResult.text = formatResult
            textViewDate.text = formattedDate


        }

        private fun formatDate(date: Date): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return "Data: ${sdf.format(date)}"
        }

    }
}