package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mathapp.challenges.classes.Answer
import com.example.mathapp.challenges.classes.Answers
import com.example.mathapp.challenges.classes.Question
import java.time.Duration
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

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
    var categoryId: String = String()


    constructor()

    constructor(currentIdQuestion: Int, viewList: ArrayList<TextView>,categoryName: String, questionList: ArrayList<Question>,
                maxQuestion: Int, progressBar: ProgressBar, spentTimeOnTheQuiz: LocalTime?,
                delayTimeQuestion: Long, timeToSpentOnTheQuestion: Long, difficultyLevel: Int,
                difficultName: String, categoryId: String
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
        this.categoryId = categoryId
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
        intent.putExtra("categoryId", categoryId)
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

    private fun randNumbers(question: Question): Question
    {

        val s: MutableSet<Int> = mutableSetOf()
        while (s.size < 4) { s.add((0..3).random()) }
        val randomList = s.toList()
        val answerList: ArrayList<Answer> = arrayListOf(question.answers.answer1,
            question.answers.answer2,
            question.answers.answer3,
            question.answers.answer4
        )
        println("random index: ${randomList[0]} || ${randomList[1]} || ${randomList[2]} || ${randomList[3]}")
        var helpList: ArrayList<Answer> = answerList
        for (i in 0 until answerList.size)
        {
            helpList.set(randomList[i],answerList[i])
        }

        val returnQuestion = question
        returnQuestion.answers.answer1 = helpList[0]
        returnQuestion.answers.answer2 = helpList[1]
        returnQuestion.answers.answer3 = helpList[2]
        returnQuestion.answers.answer4 = helpList[3]

        return returnQuestion

    }


    private fun randAnswers(question:Question): Question{
        var randomHelpList: ArrayList<Answer> = arrayListOf(question.answers.answer1,
            question.answers.answer2,
            question.answers.answer3,
            question.answers.answer4
        )


        var randomIdList: ArrayList<Int> = arrayListOf(0,1,2,3)
        var helpList: ArrayList<Int> = arrayListOf()
        var mutableSet: MutableSet<Int> = mutableSetOf()

        for (id in 0 until randomIdList.size)
        {
            var randId: Int = Random.nextInt(0, randomIdList.size)
            helpList.add(randomIdList[randId])

        }

        mutableSet = helpList.toMutableSet()

        if(mutableSet.size < randomIdList.size)
        {
            while (mutableSet.size < randomIdList.size)
            {
                var randomId: Int = Random.nextInt(0, randomIdList.size)
                mutableSet.add(randomIdList[randomId])
            }
        }
        randomIdList = arrayListOf()

        for (hs in mutableSet)
        {
            randomIdList.add(hs)
        }

        var randQuestionList: Question = Question()
        randQuestionList.answers.answer1 = randomHelpList[randomIdList[0]]
        randQuestionList.answers.answer2 = randomHelpList[randomIdList[1]]
        randQuestionList.answers.answer3 = randomHelpList[randomIdList[2]]
        randQuestionList.answers.answer4 = randomHelpList[randomIdList[3]]



        return randQuestionList
    }

    private fun showQuestion(questionList: ArrayList<Question>, id: Int)
    {
        val questionFirst: Question = questionList[id]
        val question: Question = randAnswers(questionFirst)

            //viewList[0].text = question.title


            viewList[1].text = question.answers.answer1.title
            viewList[2].text = question.answers.answer2.title
            viewList[3].text = question.answers.answer3.title
            viewList[4].text = question.answers.answer4.title




    }
}