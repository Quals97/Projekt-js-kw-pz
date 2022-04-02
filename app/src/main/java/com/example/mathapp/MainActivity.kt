package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mathapp.challenges.ChallengesActivity
import com.example.mathapp.science.ScienceActivity
import com.example.mathapp.science.ScienceAdapter
import com.example.mathapp.useroption.OptionsActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    var doubleBackClick: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        study_button.setOnClickListener(scienceListener)
        options_button.setOnClickListener(optionsListener)
        challenge_button.setOnClickListener(challengeListener)

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

    private val challengeListener: View.OnClickListener = View.OnClickListener {
        goToChallengeActivity()
    }

    private val scienceListener: View.OnClickListener = View.OnClickListener {
        goToScienceActivity()
    }

    private val optionsListener: View.OnClickListener = View.OnClickListener {
        goToOptionsActivity()
    }

    private fun goToChallengeActivity(){
        val intent = Intent(applicationContext, ChallengesActivity::class.java)
        startActivity(intent)
    }

    private fun goToScienceActivity(){
        val intent = Intent(applicationContext,ScienceActivity::class.java)
        startActivity(intent)

    }

    private fun goToOptionsActivity(){
        val intent = Intent(applicationContext,OptionsActivity::class.java)
        startActivity(intent)

    }

}