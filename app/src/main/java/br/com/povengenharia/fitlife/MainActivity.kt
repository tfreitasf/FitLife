package br.com.povengenharia.fitlife


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    private lateinit var rvMain: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawabledId = R.drawable.imc,
                textStringId = R.string.tv_bmi_title,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawabledId = R.drawable.historicoimc,
                textStringId = R.string.tv_bmi_history_title,
                color = Color.YELLOW
            )
        )


        val adapter = MainAdapter(mainItems)

        val layoutManager = GridLayoutManager(this, 2)

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = layoutManager


    }

    private inner class MainAdapter(private val mainItems: List<MainItem>) :
        RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)

        }

        override fun getItemCount(): Int {
            return mainItems.size


        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)


        }

    }

    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val title: TextView = itemView.findViewById(R.id.tv_icon_name)
            title.setText(item.textStringId)
            val img: ImageView = itemView.findViewById(R.id.iv_icon_image)
            img.setImageResource(item.drawabledId)
            val container : LinearLayout = itemView as LinearLayout
            container.setBackgroundColor(item.color)

        }
    }
}
