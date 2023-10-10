package br.com.povengenharia.fitlife

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.povengenharia.fitlife.model.Calc
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvDataHistory: RecyclerView
    private val dataHistoryList = mutableListOf<Calc>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            dataHistoryList.clear()
            dataHistoryList.addAll(response)

            runOnUiThread {
                val adapter = dataHistoryAdapter(dataHistoryList)
                rvDataHistory = findViewById(R.id.rv_history)
                rvDataHistory.adapter = adapter
                rvDataHistory.layoutManager = LinearLayoutManager(this)
            }
        }.start()
    }

    private inner class dataHistoryAdapter(
        private val dataList: List<Calc>
    ):
            RecyclerView.Adapter<DataHistoryHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHistoryHolder {
            val view = layoutInflater.inflate(R.layout.history_item, parent, false)
            return DataHistoryHolder(view)
        }

        override fun onBindViewHolder(holder: DataHistoryHolder, position: Int) {
            val calc = dataHistoryList[position]
            holder.bind(calc)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

    }

    private inner class DataHistoryHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(calc: Calc){
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