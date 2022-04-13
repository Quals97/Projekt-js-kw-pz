package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mathapp.challenges.classes.Question
import java.time.Duration
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class QuestionClickListener: View.OnClickListener {
    var currentIdQuestion: Int = 0
    var viewList: ArrayList<TextView> = arrayListOf()
    var categoryName: String = String()
    var questionList: ArrayList<Question> = arrayListOf()
    var progressBar: ProgressBar? = null
    @RequiresApi(Build.VERSION_CODES.O)
    var startQuestion: LocalTime? = null
    var finQuestion: LocalTime? = null
   // var animation: ObjectAnimator? = null
    var pointsReceived: Int = 0
    var maxQuestion: Int = 0
    var delayTimeQuestion: Long =0
    var timeToSpentOnTheQuestion: Long = 0
    var spentAllTime: Int = 0
    var difficultyLevel: Int = 0
    var difficultName: String = String()

    constructor()

    constructor(currentIdQuestion: Int, viewList: ArrayList<TextView>,categoryName: String, questionList: ArrayList<Question>,
                maxQuestion: Int, progressBar: ProgressBar, spentTimeOnTheQuiz: LocalTime?,
                delayTimeQuestion: Long, timeToSpentOnTheQuestion: Long, difficultyLevel: Int,
                difficultName: String
                )
    {
        this.currentIdQuestion = currentIdQuestion
        this.viewList = viewList
        this.categoryName = categoryName
        this.questionList = questionList
        this.maxQuestion = maxQuestion
        this.progressBar = progressBar
        this.startQuestion = spentTimeOnTheQuiz
        this.delayTimeQuestion = delayTimeQuestion
        this.timeToSpentOnTheQuestion = timeToSpentOnTheQuestion
        this.difficultyLevel = difficultyLevel
        this.difficultName = difficultName
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {

        finQuestion = LocalTime.now()
        var duration = Duration.between(startQuestion, finQuestion)

        if(v!!.id == viewList[0].id)
        {
            //println("SpentTime ${timeToSpentOnTheQuestion/1000}")
            spentAllTime+= (timeToSpentOnTheQuestion/1000).toInt()

        }else{

            //println("SpentTime: ${duration.seconds-delayTimeQuestion/1000}")
            spentAllTime+= (duration.seconds-delayTimeQuestion/1000).toInt()

        }



        startQuestion = finQuestion

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
        intent.putExtra("spentTime", spentAllTime)
        intent.putExtra("categoryName", categoryName)
        intent.putExtra("difficultyLevel", difficultyLevel)
        intent.putExtra("difficultName", difficultName)
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


            //viewList[0].text = question.title


            viewList[1].text = question.answers.answer1.title
            viewList[2].text = question.answers.answer2.title
            viewList[3].text = question.answers.answer3.title
            viewList[4].text = question.answers.answer4.title




    }
}