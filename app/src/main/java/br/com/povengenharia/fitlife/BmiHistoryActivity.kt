package br.com.povengenharia.fitlife

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BmiHistoryActivity : AppCompatActivity() {

    private lateinit var rvBmiHistory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)

        val adapter = BmiHistoryAdapter()
        rvBmiHistory = findViewById(R.id.rv_bmi_history)
        rvBmiHistory.adapter = adapter
        rvBmiHistory.layoutManager = LinearLayoutManager(this)




    }

    private inner class BmiHistoryAdapter : RecyclerView.Adapter<BmiHistoryHolder>(

    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmiHistoryHolder {
            val view = layoutInflater.inflate(R.layout.bmi_history_item, parent, false)
            return BmiHistoryHolder(view)
            Log.i("Teste","aqui está onCreateViewHolder")

        }

        override fun onBindViewHolder(holder: BmiHistoryHolder, position: Int) {
            Log.i("Teste","aqui está onBindViewHolder")

        }

        override fun getItemCount(): Int {
            return 15
            Log.i("Teste","aqui está getItemCount")
        }
    }


    private class BmiHistoryHolder(view: View) : RecyclerView.ViewHolder(view)
}