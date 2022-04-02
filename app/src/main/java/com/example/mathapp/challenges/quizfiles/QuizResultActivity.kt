package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import kotlinx.android.synthetic.main.quiz_result_activity.*

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_result_activity)

        result_points_received.text = intent.getIntExtra("points",-1).toString()

        result_btn.setOnClickListener(buttonListener)


    }

    private val buttonListener = View.OnClickListener {
        goToMainActivity()

    }

    private fun goToMainActivity()
    {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}