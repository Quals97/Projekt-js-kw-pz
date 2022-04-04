package com.example.mathapp.challenges.quizfiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.challenges.classes.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.quiz_activity.*

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

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

                            showQuestion(questionCategories[categoryId].questions, currentIdQuestion)

                            var questionClickListener = QuestionClickListener(
                                currentIdQuestion,
                                arrayListOf(
                                    questionTitle,
                                    answer1,
                                    answer2,
                                    answer3,
                                    answer4,
                                ),
                                questionCategories[categoryId].questions,
                                difficultyLevel
                            )

                            answer1.setOnClickListener(questionClickListener)
                            answer2.setOnClickListener(questionClickListener)
                            answer3.setOnClickListener(questionClickListener)
                            answer4.setOnClickListener(questionClickListener)





                        }


                    }

                    override fun onCancelled(error: DatabaseError) {


                    }


                })






            }
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