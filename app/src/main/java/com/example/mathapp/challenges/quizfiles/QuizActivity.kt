package com.example.mathapp.challenges.quizfiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModulesPassed
import com.example.mathapp.authentication.classes.User
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.challenges.classes.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.position_in_module_exam_adapter.*
import kotlinx.android.synthetic.main.quiz_activity.*
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class QuizActivity : AppCompatActivity() {
    val sizeOfQustionInQuest: Int = 5
    var totalTimeOnQestion: Long = 20000


    var delayPerQuestion: Long = 3000
    var correctAnswerColor = Color.parseColor("#CD038350")
    var wrongAnswerColor = Color.parseColor("#780606")
    var defaultAnswerColor = Color.parseColor("#878783")
    @RequiresApi(Build.VERSION_CODES.O)
    var startQuiz: LocalTime? = LocalTime.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)



            var delayPerQuestion = intent.getStringExtra("userSettingsTime")!!.toLong()
            //println("DELAYSETTINGS ${delayPerQuestion}")

            val progressBarTimer = findViewById<ProgressBar>(R.id.progress_bar_timer)
            var animation = ObjectAnimator.ofInt(progressBarTimer, "progress", 100,0)
            progressBarTimer.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))

            animation.duration = totalTimeOnQestion
            animation.interpolator = DecelerateInterpolator()
            animation.start()


            var questionTitle = findViewById<TextView>(R.id.question_title_1)
            var answer1 = findViewById<TextView>(R.id.btn_question_answer_1)
            var answer2 = findViewById<TextView>(R.id.btn_question_answer_2)
            var answer3 = findViewById<TextView>(R.id.btn_question_answer_3)
            var answer4 = findViewById<TextView>(R.id.btn_question_answer_4)

            var A = findViewById<TextView>(R.id.text_view_A)
            var B = findViewById<TextView>(R.id.text_view_B)
            var C = findViewById<TextView>(R.id.text_view_C)
            var D = findViewById<TextView>(R.id.text_view_D)

            A.setTextColor(Color.WHITE)
            B.setTextColor(Color.WHITE)
            C.setTextColor(Color.WHITE)
            D.setTextColor(Color.WHITE)



            if (intent.hasExtra("difficultyLevel"))
            {
                var currentIdQuestion: Int = 0
                val difficultyLevel: Int = intent.getIntExtra("difficultyLevel", -1)
                val categoryId: Int = intent.getIntExtra("position",-1)
                val difficultName = intent.getStringExtra("difficultName")
                println("CategoryId: ${categoryId}")
                println("DIFF: ${difficultyLevel}")

                val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app//").reference
                db.child("questionsCategories").addValueEventListener(object: ValueEventListener{
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            var questionCategories: ArrayList<Category> = arrayListOf()
                            for (cat in snapshot.children)
                            {
                                val category = cat.getValue(Category::class.java)
                                questionCategories.add(category!!)
                            }

                            var selectedQuestions: ArrayList<Question> = arrayListOf()

                            when(difficultyLevel)
                            {
                                1 -> {
                                    selectedQuestions = questionCategories[categoryId].questions

                                }
                                2 -> {
                                    selectedQuestions = questionCategories[categoryId].questions1
                                }
                                3 -> {
                                    selectedQuestions = questionCategories[categoryId].questions2
                                }

                            }
                            var helpSelectedQuestion: ArrayList<Question> = randQuestion(selectedQuestions)
                            selectedQuestions = arrayListOf()
                            selectedQuestions = helpSelectedQuestion






                            //showQuestion(questionCategories[categoryId].questions, currentIdQuestion)
                            showQuestion(selectedQuestions, currentIdQuestion)

                            var questionClickListener = QuestionClickListener(
                                currentIdQuestion,
                                arrayListOf(
                                    questionTitle,
                                    answer1,
                                    answer2,
                                    answer3,
                                    answer4,
                                ),
                                questionCategories[categoryId].categoryName,
                                selectedQuestions,
                                difficultyLevel,
                                progressBarTimer,
                                startQuiz!!,
                                delayPerQuestion,
                                totalTimeOnQestion,
                                difficultyLevel,
                                difficultName!!,
                                questionCategories[categoryId].id
                            )
                            var timerCountDownTimer: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener,
                                progressBarTimer, animation, currentIdQuestion, selectedQuestions,selectedQuestions)
//                            var timerCountDownTimer1: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
//                            var timerCountDownTimer2: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
//                            var timerCountDownTimer3: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
//                            var timerCountDownTimer4: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            timerCountDownTimer.start()

                            println("QuizActivity: ${selectedQuestions[categoryId].id}")

                            answer1.setOnClickListener{

                                    if(answer1.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                    {
                                        it.setBackgroundColor(correctAnswerColor)
                                        A.setTextColor(correctAnswerColor)
                                        Timer("backgraundcolorTrue", false).schedule(delayPerQuestion)
                                        {
                                            it.setBackgroundColor(defaultAnswerColor)
                                            A.setTextColor(Color.WHITE)
                                        }

                                        println("Zielony")

                                    }else{

                                        it.setBackgroundColor(wrongAnswerColor)
                                        A.setTextColor(wrongAnswerColor)
                                        Timer("backgraundcolorFalse", false).schedule(delayPerQuestion)
                                        {
                                            it.setBackgroundColor(defaultAnswerColor)
                                            A.setTextColor(Color.WHITE)
                                        }

                                        println("Czerwony")
                                    }




                                    animation.pause()
                                    timerCountDownTimer.cancel()
                                        Timer("next_question", false).schedule(delayPerQuestion)
                                        {
                                            questionClickListener.onClick(questionClickListener.viewList[1])
                                            Thread(Runnable {


                                                this@QuizActivity.runOnUiThread(java.lang.Runnable {
                                                    var id = questionClickListener.currentIdQuestion
                                                    if (questionClickListener.currentIdQuestion == selectedQuestions.size)
                                                    {
                                                        timerCountDownTimer.cancel()
                                                        finish()
                                                    }
                                                    if(id < selectedQuestions.size)
                                                    {

                                                        question_title_1.text = "${selectedQuestions[id].title}"
//                                                        question_title_1.text = "${questionClickListener.}"
                                                        //answer1.text = "${selectedQuestions[id].answers.answer1.title}"

                                                    }

                                                })
                                            }).start()

                                            timerCountDownTimer.start()
                                        }
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            animation.start()
                                        },delayPerQuestion)



                                    println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")








                            }
                            answer2.setOnClickListener{
                                if(answer2.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {
                                    it.setBackgroundColor(correctAnswerColor)
                                    B.setTextColor(correctAnswerColor)
                                    Timer("backgraundcolorTrue", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        B.setTextColor(Color.WHITE)
                                    }


                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(wrongAnswerColor)
                                    B.setTextColor(wrongAnswerColor)
                                    Timer("backgraundcolorFalse", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        B.setTextColor(Color.WHITE)
                                    }

                                    println("Czerwony")
                                }





                                animation.pause()
                                timerCountDownTimer.cancel()
                                Timer("next_question", false).schedule(delayPerQuestion)
                                {
                                    questionClickListener.onClick(questionClickListener.viewList[2])
                                    Thread(Runnable {


                                        this@QuizActivity.runOnUiThread(java.lang.Runnable {
                                            var id = questionClickListener.currentIdQuestion
                                            if (questionClickListener.currentIdQuestion == selectedQuestions.size)
                                            {
                                                timerCountDownTimer.cancel()
                                                finish()
                                            }
                                            if(id < selectedQuestions.size)
                                            {

                                                question_title_1.text = "${selectedQuestions[id].title}"

                                            }

                                        })
                                    }).start()

                                    timerCountDownTimer.start()
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    animation.start()
                                },delayPerQuestion)

                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")

                            }
                            answer3.setOnClickListener {
                                if(answer3.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {
                                    it.setBackgroundColor(correctAnswerColor)
                                    C.setTextColor(correctAnswerColor)
                                    Timer("backgraundcolorTrue", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        C.setTextColor(Color.WHITE)
                                    }


                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(wrongAnswerColor)
                                    C.setTextColor(wrongAnswerColor)
                                    Timer("backgraundcolorFalse", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        C.setTextColor(Color.WHITE)
                                    }
                                    println("Czerwony")
                                }
                                animation.pause()
                                timerCountDownTimer.cancel()
                                Timer("next_question", false).schedule(delayPerQuestion)
                                {
                                    questionClickListener.onClick(questionClickListener.viewList[3])
                                    Thread(Runnable {


                                        this@QuizActivity.runOnUiThread(java.lang.Runnable {
                                            var id = questionClickListener.currentIdQuestion

                                            if (questionClickListener.currentIdQuestion == selectedQuestions.size)
                                            {
                                                timerCountDownTimer.cancel()
                                                finish()
                                            }
                                            if(id < selectedQuestions.size)
                                            {
                                                question_title_1.text = "${selectedQuestions[id].title}"

                                            }

                                        })
                                    }).start()

                                    timerCountDownTimer.start()
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    animation.start()
                                },delayPerQuestion)


                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")
                            }
                            answer4.setOnClickListener {
                                if(answer4.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {
                                    it.setBackgroundColor(correctAnswerColor)
                                    D.setTextColor(correctAnswerColor)
                                    Timer("backgraundcolorTrue", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        D.setTextColor(Color.WHITE)
                                    }

                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(wrongAnswerColor)
                                    D.setTextColor(wrongAnswerColor)
                                    Timer("backgraundcolorFalse", false).schedule(delayPerQuestion)
                                    {
                                        it.setBackgroundColor(defaultAnswerColor)
                                        D.setTextColor(Color.WHITE)
                                    }
                                    println("Czerwony")
                                }

                                animation.pause()
                                timerCountDownTimer.cancel()
                                Timer("next_question", false).schedule(delayPerQuestion)
                                {
                                    questionClickListener.onClick(questionClickListener.viewList[4])
                                    Thread(Runnable {


                                        this@QuizActivity.runOnUiThread(java.lang.Runnable {
                                            var id = questionClickListener.currentIdQuestion

                                            if (questionClickListener.currentIdQuestion == selectedQuestions.size)
                                            {
                                                timerCountDownTimer.cancel()
                                                finish()
                                            }
                                            if(id < selectedQuestions.size)
                                            {
                                                question_title_1.text = "${selectedQuestions[id].title}"

                                            }

                                        })
                                    }).start()

                                    timerCountDownTimer.start()
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    animation.start()
                                },delayPerQuestion)


                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")

                            }





                        }


                    }

                    override fun onCancelled(error: DatabaseError) {


                    }


                })






            }
    }
    private fun randQuestion(questionList:ArrayList<Question>): ArrayList<Question>{
        var randQuestionList: ArrayList<Question> = arrayListOf()
        var hashSet: HashSet<Question> = HashSet()


        for (i in 0 until questionList.size)
        {

            var randId: Int = Random.nextInt(0, questionList.size)
            randQuestionList.add(questionList[randId])

        }
        hashSet = randQuestionList.toHashSet()
        var helpList: ArrayList<Question> = arrayListOf()


        if(hashSet.size < questionList.size)
        {
            while (hashSet.size < questionList.size)
            {
                var randomId: Int = Random.nextInt(0, questionList.size)
                hashSet.add(questionList[randomId])
            }
        }

        for (hs in hashSet)
        {
            helpList.add(hs)
        }
        randQuestionList = arrayListOf()

        for(i in 0 until sizeOfQustionInQuest)
        {
            randQuestionList.add(helpList[i])
        }

        return randQuestionList
    }


    private fun setIntervalTimer(textView: TextView?, questionClickListener: QuestionClickListener, progressBar: ProgressBar,
                                 animation: ObjectAnimator, currentQuestionId: Int, questionList: ArrayList<Question>,
                                 selectedQuestions:ArrayList<Question>
                                 ): CountDownTimer {


        val timer = object : CountDownTimer(totalTimeOnQestion, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                var currentTime= animation.getAnimatedValue("progress").toString().toInt()

                var counter = millisUntilFinished / 1000
                if(currentTime in 21..45)
                {
                    progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#CDFF920C"))
                }else if (currentTime <= 15)
                {
                    progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#CDAF1010"))
                }
                timer_quiz_activity.text = "${(millisUntilFinished/1000) +1} sek"

                println("Time: ${millisUntilFinished}")
//                println("TIME: ${millisUntilFinished}")
                //questionTimer.text = counter.toString()



            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {

                progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))

                animation.start()

                cancel()
                println("Koniec zewnętrzne")
                if (questionClickListener.currentIdQuestion <= questionClickListener.questionList.size)
                {
                    questionClickListener.onClick(textView)
                    Thread(Runnable {


                        this@QuizActivity.runOnUiThread(java.lang.Runnable {
                            var id = questionClickListener.currentIdQuestion
                            if(id < selectedQuestions.size)
                            {
                                question_title_1.text = "${selectedQuestions[id].title}"

                            }

                        })
                    }).start()



                    if (questionClickListener.currentIdQuestion < questionClickListener.questionList.size)
                    {
                        start()
                    }
                }



            }

        }


        return timer

    }



    private fun showQuestion(questionsList: ArrayList<Question>,id: Int)
    {
        val question: Question =  questionsList[id]

        question_title_1.text = question.title
        btn_question_answer_1.text = question.answers.answer1.title
        btn_question_answer_2.text = question.answers.answer2.title
        btn_question_answer_3.text = question.answers.answer3.title
        btn_question_answer_4.text = question.answers.answer4.title


    }

}