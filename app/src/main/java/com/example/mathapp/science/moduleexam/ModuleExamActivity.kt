package com.example.mathapp.science.moduleexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModulesPassed
import com.example.mathapp.challenges.classes.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.module_exam_activity.*

class ModuleExamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_exam_activity)
        var modulesPassed: ModulesPassed = ModulesPassed()
        if (intent.hasExtra("idModule"))
        {
            val idModulesPassed = intent.getStringExtra("idModulesPassed")
            val pointsModulesPassed = intent.getStringExtra("pointsModulesPassed")
            val statusModulesPassed = intent.getStringExtra("statusModulesPassed")
            val nameModulesPassed = intent.getStringExtra("nameModulesPassed")
            modulesPassed.id = idModulesPassed.toString()
            modulesPassed.status = statusModulesPassed.toString()
            modulesPassed.points = pointsModulesPassed.toString()
            modulesPassed.name = nameModulesPassed.toString()

            println("ModuleExamAcivityModulesActivityid: ${idModulesPassed}")
            println("ModuleExamAcivityModulesActivityPoints: ${pointsModulesPassed}")
            println("ModuleExamAcivityModulesActivityStatus: ${statusModulesPassed}")
            println("ModuleExamAcivityModulesActivityName: ${nameModulesPassed}")
        }

        var db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("questionsCategories").addValueEventListener(object : ValueEventListener{
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

                    //println("List: ${categories.filter { n -> n.categoryName == "Liczby naturalne" } }" )
                    recycler_view_module_exam.layoutManager = LinearLayoutManager(applicationContext)
                    recycler_view_module_exam.adapter = ModuleExamAdapter(applicationContext, category, btn_module_exam_activity,
                                                                                completedList, modulesPassed, null
                                                                            )
                    btn_module_exam_activity.setOnClickListener {

                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })




    }
}