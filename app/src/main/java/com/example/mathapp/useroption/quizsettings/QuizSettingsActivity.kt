package com.example.mathapp.useroption.quizsettings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.QuizSettings
import com.example.mathapp.authentication.classes.UserSettings
import com.example.mathapp.challenges.quizfiles.CategoryActivity
import com.example.mathapp.challenges.quizfiles.QuizActivity
import com.example.mathapp.useroption.OptionsActivity
import com.google.android.material.slider.Slider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_quiz_settings.*

class QuizSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_settings)

        var delayTime: Long = intent.getStringExtra("userSettingsDelayTime")!!.toLong()

        time_from_seek_bar.text = "${delayTime/1000} ${applicationContext.getString(R.string.second)}"
        seekBar.progress = (delayTime/1000).toInt()
        //time_from_seek_bar.text = "${seekBar.progress} ${applicationContext.getString(R.string.second)}"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // override the onProgressChanged method to perform operations
            // whenever the there a change in SeekBar
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                time_from_seek_bar.text = "${progress} ${applicationContext.getString(R.string.second)}"




            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                var quizSettings = QuizSettings((seekBar!!.progress*1000).toString())
                var userSettings = UserSettings(quizSettings)


                db.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("userSettings").setValue(userSettings)
            }
        })




    }

    override fun onBackPressed() {

        val intent = Intent(applicationContext, OptionsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}