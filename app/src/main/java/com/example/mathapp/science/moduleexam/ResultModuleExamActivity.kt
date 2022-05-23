package com.example.mathapp.science.moduleexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.levels.Level
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_result_module_exam.*

class ResultModuleExamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_module_exam)

        println("RESULTMODULEEXAMACTIVITY: ${intent.getStringExtra("level")} || ${intent.getStringExtra("points")}")

        var userAnswersB = intent.getBundleExtra("userAnswerExtra")
        var userAnswers: ArrayList<Pair<String, String>> = userAnswersB!!.getSerializable("userAnswer") as ArrayList<Pair<String, String>>

        for (uA in userAnswers)
        {
            println("${uA.first} || ${uA.second}")
        }



        var db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("questionsCategories").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    var categories: ArrayList<Category> = arrayListOf()
                    for (c in snapshot.children)
                    {
                        val categoty = c.getValue(Category::class.java)
                        categories.add(categoty!!)

                    }


                    val categoryName: String = intent.getStringExtra("categoryName").toString()
                    var categoriesList = categories.filter { n -> n.categoryName == "${categoryName}" }
                    val category: Category = categoriesList[0]

                    val completedList: ArrayList<Pair<String, String>> = arrayListOf()
                    for (c in category.questions0)
                    {
                        completedList.add(Pair("",""))

                    }

                    val dbLevel = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                    dbLevel.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child("level").addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists())
                                {
                                    var level: Level = Level()
                                    for (lvl in snapshot.children)
                                    {
                                        //println("LVL: ${lvl}")
                                        if (lvl.key == "level"){
                                            level.level = lvl.value.toString()
                                        }else if(lvl.key == "points"){
                                            level.points = lvl.value.toString()
                                        }



                                    }

                                    recycler_view_result_module_exam.layoutManager = LinearLayoutManager(applicationContext)
                                    recycler_view_result_module_exam.adapter = ModuleExamAdapter(applicationContext, category, btn_result_module_exam_activity,
                                        userAnswers, null, level
                                    )


                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }


                        })






                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })





    }
}