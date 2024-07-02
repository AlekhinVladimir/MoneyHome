package com.example.moneyhome.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moneyhome.R
import com.example.moneyhome.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MainFragment())
                .commit()
        }
    }
}