package com.example.mathapp.challenges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.lengthquiz.LengthOfTheQuiz
import com.example.mathapp.challenges.classes.lengthquiz.QuizSettings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.challenges_activity.*

class ChallengesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challenges_activity)


        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("QuizSettings").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    var quizSettingsList: ArrayList<QuizSettings> = arrayListOf()
                    for (qS in snapshot.children)
                    {
                        println("qS: ${qS}")
                        var quizSetting = qS.getValue(QuizSettings::class.java)
                        quizSettingsList.add(quizSetting!!)

                        recycler_view_difficulty_levels.layoutManager = LinearLayoutManager(applicationContext)
                        recycler_view_difficulty_levels.adapter = DifficutyLevelsAdapter(applicationContext, quizSettingsList)

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



    }
}