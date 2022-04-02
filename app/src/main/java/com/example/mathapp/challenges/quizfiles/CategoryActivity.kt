package com.example.mathapp.challenges.quizfiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.category_activity.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)




        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("questionsCategories").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    var questionCategories: ArrayList<Category> = arrayListOf()
                    for (cat in snapshot.children)
                    {
                        println("CAT: ${cat}")
                        val category = cat.getValue(Category::class.java)
                        questionCategories.add(category!!)
                    }

                    if (intent.hasExtra("difficultyLevel"))
                    {
                        val difficultyLevel = intent.getStringExtra("difficultyLevel")
                        category_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                        category_recycler_view.adapter = CategoryAdapter(applicationContext, questionCategories, difficultyLevel!!.toInt())
                    }



                }

            }

            override fun onCancelled(error: DatabaseError) {
            }


        })





    }
}