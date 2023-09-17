package br.com.povengenharia.fitlife


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var btnBMI: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBMI = findViewById(R.id.btn_bmi)

        btnBMI.setOnClickListener {
            val i = Intent(this, ImcActivity::class.java)
            startActivity(i)

        }


    }
}

