package com.example.mathapp.challenges.quizfiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextWatcher
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.challenges.classes.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.quiz_activity.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class QuizActivity : AppCompatActivity() {
    var totalTimeOnQestion: Long = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

            val progressBarTimer = findViewById<ProgressBar>(R.id.progress_bar_timer)
            var animation = ObjectAnimator.ofInt(progressBarTimer, "progress", 100,0)
            progressBarTimer.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))

            animation.duration = 10000
            animation.interpolator = DecelerateInterpolator()
            animation.start()


            var questionTitle = findViewById<TextView>(R.id.questionTitle)
            var answer1 = findViewById<TextView>(R.id.btn_question_answer_1)
            var answer2 = findViewById<TextView>(R.id.btn_question_answer_2)
            var answer3 = findViewById<TextView>(R.id.btn_question_answer_3)
            var answer4 = findViewById<TextView>(R.id.btn_question_answer_4)


            if (intent.hasExtra("difficultyLevel"))
            {
                var currentIdQuestion: Int = 0
                val difficultyLevel: Int = intent.getIntExtra("difficultyLevel", -1)
                val categoryId: Int = intent.getIntExtra("position",-1)
                println("CategoryId: ${categoryId}")
                println("DIFF: ${difficultyLevel}")

                val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app//").reference
                db.child("questionsCategories").addValueEventListener(object: ValueEventListener{
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
                                selectedQuestions,
                                difficultyLevel,
                                progressBarTimer
                            )
                            var timerCountDownTimer: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            var timerCountDownTimer1: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            var timerCountDownTimer2: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            var timerCountDownTimer3: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            var timerCountDownTimer4: CountDownTimer = setIntervalTimer(questionTitle, questionClickListener, progressBarTimer, animation)
                            timerCountDownTimer.start()



                            answer1.setOnClickListener{

                                    if(answer1.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                    {

                                        it.setBackgroundColor(Color.GREEN)

                                        Timer("set_options_btn_1", true).schedule(500){
                                            this.run{


                                                it.setBackgroundColor(Color.parseColor("#878783"))
                                            }

                                        }

//                                        Timer().schedule(object : TimerTask(){
//                                            override fun run() {
//                                                println("klik")
//                                                questionClickListener.onClick(answer1)
//                                            }
//
//                                        }, 5000)
                                        println("Zielony")

                                    }else{
                                        it.setBackgroundColor(Color.RED)
                                        Timer("set_options_btn_1", false).schedule(500){
                                            it.setBackgroundColor(Color.parseColor("#878783"))

                                        }


//                                        Timer().schedule(object : TimerTask(){
//                                            override fun run() {
//                                                println("klik")
//                                                questionClickListener.onClick(answer1)
//                                            }
//
//                                        }, 5000)
                                        println("Czerwony")
                                    }


                                    animation.pause()

                                    questionClickListener.onClick(answer1)
                                    timerCountDownTimer.start()
                                    timerCountDownTimer1.cancel()
                                    timerCountDownTimer2.cancel()
                                    timerCountDownTimer3.cancel()
                                    timerCountDownTimer4.cancel()
                                    animation.start()
                                    println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")








                            }
                            answer2.setOnClickListener{
                                if(answer2.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {

                                    it.setBackgroundColor(Color.GREEN)

                                    Timer("set_options_btn_1", true).schedule(500){
                                        this.run{


                                            it.setBackgroundColor(Color.parseColor("#878783"))
                                        }



                                    }

                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(Color.RED)
                                    Timer("set_options_btn_1", false).schedule(500){
                                        it.setBackgroundColor(Color.parseColor("#878783"))

                                    }
                                    println("Czerwony")
                                }




                                animation.pause()
                                questionClickListener.onClick(answer2)

                                timerCountDownTimer.cancel()
                                timerCountDownTimer1.start()
                                timerCountDownTimer2.cancel()
                                timerCountDownTimer3.cancel()
                                timerCountDownTimer4.cancel()
                                animation.start()
                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")

                            }
                            answer3.setOnClickListener {
                                if(answer3.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {

                                    it.setBackgroundColor(Color.GREEN)

                                    Timer("set_options_btn_1", true).schedule(500){
                                        this.run{


                                            it.setBackgroundColor(Color.parseColor("#878783"))
                                        }



                                    }

                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(Color.RED)
                                    Timer("set_options_btn_1", false).schedule(500){
                                        it.setBackgroundColor(Color.parseColor("#878783"))

                                    }
                                    println("Czerwony")
                                }


                                animation.pause()
                                questionClickListener.onClick(answer3)

                                timerCountDownTimer.cancel()
                                timerCountDownTimer1.cancel()
                                timerCountDownTimer2.start()
                                timerCountDownTimer3.cancel()
                                timerCountDownTimer4.cancel()
                                animation.start()
                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")
                            }
                            answer4.setOnClickListener {
                                if(answer4.text.toString() == selectedQuestions[questionClickListener.currentIdQuestion].answers.correctAnswer.title)
                                {

                                    it.setBackgroundColor(Color.GREEN)

                                    Timer("set_options_btn_1", true).schedule(500){
                                        this.run{


                                            it.setBackgroundColor(Color.parseColor("#878783"))
                                        }



                                    }

                                    println("Zielony")

                                }else{
                                    it.setBackgroundColor(Color.RED)
                                    Timer("set_options_btn_1", false).schedule(500){
                                        it.setBackgroundColor(Color.parseColor("#878783"))

                                    }
                                    println("Czerwony")
                                }



                                animation.pause()
                                questionClickListener.onClick(answer4)

                                timerCountDownTimer.cancel()
                                timerCountDownTimer1.cancel()
                                timerCountDownTimer2.cancel()
                                timerCountDownTimer3.start()
                                timerCountDownTimer4.cancel()
                                animation.start()
                                println("currentIdQuestion: ${questionClickListener.currentIdQuestion-1}")

                            }





                        }


                    }

                    override fun onCancelled(error: DatabaseError) {


                    }


                })






            }
    }


    private fun setIntervalTimer(textView: TextView?, questionClickListener: QuestionClickListener, progressBar: ProgressBar, animation: ObjectAnimator): CountDownTimer {


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

//                println("TIME: ${millisUntilFinished}")
                //questionTimer.text = counter.toString()



            }

            override fun onFinish() {

                progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))

                animation.start()

                cancel()
                println("Koniec zewnętrzne")
                if (questionClickListener.currentIdQuestion <= questionClickListener.questionList.size)
                {
                    questionClickListener.onClick(textView)
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

        questionTitle.text = question.title
        btn_question_answer_1.text = question.answers.answer1.title
        btn_question_answer_2.text = question.answers.answer2.title
        btn_question_answer_3.text = question.answers.answer3.title
        btn_question_answer_4.text = question.answers.answer4.title


    }

}