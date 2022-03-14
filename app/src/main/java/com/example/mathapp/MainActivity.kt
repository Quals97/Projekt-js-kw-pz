package com.example.mathapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    var doubleBackClick: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if (doubleBackClick)
        {
            finishAffinity()

        }
        if(!doubleBackClick)
        {
            Toast.makeText(applicationContext,"Tap two times to close app", Toast.LENGTH_SHORT).show()
        }
        doubleBackClick = true

        Timer("Set_value_false", false).schedule(1000){
            doubleBackClick = false
        }


    }

}