package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import kotlinx.android.synthetic.main.quiz_result_activity.*
import java.time.LocalTime

class QuizResultActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_result_activity)

        var difficultyLevel = intent.getIntExtra("difficultyLevel", -1)
        var difficultName = intent.getStringExtra("difficultName")



        result_points_received.text = "${intent.getIntExtra("points",-1).toString()}/${intent.getIntExtra("totalPoints",-1)}"
        println("spentAllTime: ${intent.getIntExtra("spentTime",-1)}")
        progress_bar_points_scored.max = intent.getIntExtra("totalPoints",-1)
        progress_bar_points_scored.progress = intent.getIntExtra("points",-1)

        difficultyName(difficultyLevel_result_activity, difficultName!!)
        spentTime(time_spent_result_activity, intent.getIntExtra("spentTime",-1))



        summary_result.text = "${intent.getStringExtra("categoryName")}"
        result_btn.setOnClickListener(buttonListener)


    }

    private fun difficultyName(textView: TextView, difficultName: String){
        textView.text = difficultName
//        when(difficultyLevel)
//        {
//            1 ->{
//                textView.text = "${applicationContext.getString(R.string.easy)}"
//            }
//            2 ->{
//                textView.text = "${applicationContext.getString(R.string.difficult)}"
//            }
//
//        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun spentTime(textView: TextView, timeSpent: Int){

        var localTime: LocalTime = LocalTime.of(0,0,0)
        localTime.plusSeconds(timeSpent.toLong())
        if (timeSpent < 60)
        {
            textView.text = "${timeSpent} ${applicationContext.getString(R.string.second)}"
        }else if(timeSpent ==60)
        {
            textView.text = "${timeSpent/60} ${applicationContext.getString(R.string.minute)}"
        }else if(timeSpent >60)
        {
            textView.text = "${timeSpent/60} ${applicationContext.getString(R.string.minute)} ${timeSpent%60} ${applicationContext.getString(R.string.second)}"
        }

//        if (localTime.minute == 0)
        //textView.text = timeSpent.toString()

    }

    private val buttonListener = View.OnClickListener {
        goToMainActivity()

    }

    private fun goToMainActivity()
    {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}