package br.com.povengenharia.fitlife

import android.app.Application
import br.com.povengenharia.fitlife.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}