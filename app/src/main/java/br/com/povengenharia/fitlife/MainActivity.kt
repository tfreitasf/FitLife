package br.com.povengenharia.fitlife


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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
        mainItems.add(
            MainItem(
                id = 3,
                drawabledId = R.drawable.bmr,
                textStringId = R.string.tv_bmr_title,
                color = Color.BLUE
            )
        )
        mainItems.add(
            MainItem(
                id=4,
                drawabledId = R.drawable.historico,
                textStringId = R.string.tv_bmr_history_title,
                color = Color.RED
            )
        )



        val adapter = MainAdapter(mainItems) {id ->
            when (id) {
                    1 -> {
                        val intent = Intent(this@MainActivity, BmiActivity::class.java)
                        startActivity(intent)
                    }

                    2 -> {
                        val intent = Intent(this@MainActivity, BmiHistoryActivity::class.java)
                        intent.putExtra("type", "imc")
                        startActivity(intent)

                    }
                    3 ->{
                        val intent = Intent(this@MainActivity, BmrActivity::class.java)
                        startActivity(intent)
                    }
                    4 ->{
                        val intent = Intent(this@MainActivity, BmrHistoryActivity::class.java)
                        startActivity(intent)
                    }


                }
            }




        val layoutManager = GridLayoutManager(this, 2)

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = layoutManager


    }






    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
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


        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val title: TextView = itemView.findViewById(R.id.tv_icon_name)
                title.setText(item.textStringId)
                val img: ImageView = itemView.findViewById(R.id.iv_icon_image)
                img.setImageResource(item.drawabledId)
                val container: CardView = itemView as CardView
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }

            }
        }

    }


}
