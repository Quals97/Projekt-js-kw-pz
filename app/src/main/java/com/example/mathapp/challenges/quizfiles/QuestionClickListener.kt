package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.example.mathapp.challenges.classes.Question

class QuestionClickListener: View.OnClickListener {
    var currentIdQuestion: Int = 0
    var viewList: ArrayList<TextView> = arrayListOf()
    var questionList: ArrayList<Question> = arrayListOf()
    var pointsReceived: Int = 0

    constructor()

    constructor(currentIdQuestion: Int, viewList: ArrayList<TextView>, questionList: ArrayList<Question>)
    {
        this.currentIdQuestion = currentIdQuestion
        this.viewList = viewList
        this.questionList = questionList
    }


    override fun onClick(v: View?) {
        println("answer: ${v!!.findViewById<TextView>(v.id).text}")
        println("PointsReceived: ${pointsReceived}")

        if(currentIdQuestion < questionList.size)
        {
            pointsReceived = counterPoints(questionList, v!!.findViewById<TextView>(v.id).text.toString(), pointsReceived,currentIdQuestion)

        }
        currentIdQuestion++
        println("id: ${currentIdQuestion}")

        if(currentIdQuestion < questionList.size)
        {
            showQuestion(questionList, currentIdQuestion)

        }else if(currentIdQuestion == questionList.size)
        {
            goToQuizResultActivity(v,pointsReceived)
        }


    }

    private fun goToQuizResultActivity(v: View, pointsReceived: Int)
    {
        val intent = Intent(v.context.applicationContext, QuizResultActivity::class.java)
        intent.putExtra("points", pointsReceived)
        v.context.startActivity(intent)
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