package com.example.mathapp.challenges.quizfiles

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mathapp.challenges.classes.Question
import java.sql.Time
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.math.max

class QuestionClickListener: View.OnClickListener {
    var currentIdQuestion: Int = 0
    var viewList: ArrayList<TextView> = arrayListOf()
    var questionList: ArrayList<Question> = arrayListOf()
    var progressBar: ProgressBar? = null
   // var animation: ObjectAnimator? = null
    var pointsReceived: Int = 0
    var maxQuestion: Int = 0

    constructor()

    constructor(currentIdQuestion: Int, viewList: ArrayList<TextView>, questionList: ArrayList<Question>,
                maxQuestion: Int, progressBar: ProgressBar)
    {
        this.currentIdQuestion = currentIdQuestion
        this.viewList = viewList
        this.questionList = questionList
        this.maxQuestion = maxQuestion
        this.progressBar = progressBar
        //this.animation = animation
    }


    override fun onClick(v: View?) {
        progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))






        if (v?.findViewById<TextView>(v!!.id) == viewList[0])
        {

        }else{


            //mySetXmalParametersOnClickListener(v)




        }

        if(currentIdQuestion < questionList.size)
        {
            pointsReceived = counterPoints(questionList, v!!.findViewById<TextView>(v.id).text.toString(), pointsReceived,currentIdQuestion)

        }

        currentIdQuestion++


        if(currentIdQuestion < questionList.size)
        {

            showQuestion(questionList, currentIdQuestion)



        }else if(currentIdQuestion == questionList.size)
        {

            goToQuizResultActivity(v,pointsReceived)
        }



        //println("id: ${currentIdQuestion}")







    }

    private fun mySetXmalParametersOnClickListener(v: View?)
    {
        v!!.setBackgroundColor(Color.GREEN)
        Timer("set_xml_value", false).schedule(500){
            v!!.setBackgroundColor(Color.parseColor("#878783"))


        }
    }


    private fun goToQuizResultActivity(v: View?, pointsReceived: Int)
    {
        val intent = Intent(v?.context?.applicationContext, QuizResultActivity::class.java)
        intent.putExtra("points", pointsReceived)
        intent.putExtra("totalPoints", questionList.size)
        v?.context?.startActivity(intent)
    }

    private fun counterPoints(questionList: ArrayList<Question>, userAnswer: String, pointsReceived: Int, id: Int): Int
    {
        var point: Int = pointsReceived

        if (questionList[id].answers.correctAnswer.title == userAnswer)
        {
            point+=1
        }
        return point
    }

    private fun showQuestion(questionList: ArrayList<Question>, id: Int)
    {
        val question: Question = questionList[id]


            viewList[0].text = question.title
            viewList[1].text = question.answers.answer1.title
            viewList[2].text = question.answers.answer2.title
            viewList[3].text = question.answers.answer3.title
            viewList[4].text = question.answers.answer4.title




    }
}