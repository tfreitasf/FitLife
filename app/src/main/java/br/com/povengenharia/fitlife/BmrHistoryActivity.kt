package br.com.povengenharia.fitlife

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.povengenharia.fitlife.model.Calc
import java.lang.IllegalStateException
import java.util.Date
import java.util.Locale

class BmrHistoryActivity : AppCompatActivity() {

    private lateinit var rvBmrHistory: RecyclerView
    private val bmrHistoryList = mutableListOf<Calc>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            bmrHistoryList.clear()
            bmrHistoryList.addAll(response)

            runOnUiThread{
                val adapter = BmrHistoryAdapter(bmrHistoryList)
                rvBmrHistory = findViewById(R.id.rv_history)
                rvBmrHistory.adapter = adapter
                rvBmrHistory.layoutManager = LinearLayoutManager(this)
            }
        }.start()
    }

    private inner class BmrHistoryAdapter(
        private val bmrList: List<Calc>
    ) :
        RecyclerView.Adapter<BmrHistoryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmrHistoryHolder {
            val view = layoutInflater.inflate(R.layout.history_item, parent, false)
            return BmrHistoryHolder(view)
        }


        override fun onBindViewHolder(holder: BmrHistoryHolder, position: Int) {
            val calc = bmrHistoryList[position]
            holder.bind(calc)
        }

        override fun getItemCount(): Int {
            return bmrList.size
        }

    }

    private inner class BmrHistoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(calc: Calc) {

            val textViewType = itemView.findViewById<TextView>(R.id.tv_type_hitory)
            val textViewResult = itemView.findViewById<TextView>(R.id.tv_result_history)
            val textViewDate = itemView.findViewById<TextView>(R.id.tv_date_history)

            textViewType.text = calc.type
            val formatedDate = formatDate(calc.createdDate)
            val formatResult = String.format("%.2f", calc.res)

            textViewResult.text = formatResult
            textViewDate.text = formatedDate

        }
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyy HH:mm", Locale.getDefault())
        return "Data: ${sdf.format(date)}"
    }


}