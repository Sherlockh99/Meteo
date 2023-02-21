package com.sherlock.gb.kotlin.meteo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sherlock.gb.kotlin.meteo.R
import com.sherlock.gb.kotlin.meteo.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance())
                .commit()
        }
    }
}