package com.example.mathapp.science

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.science.classes.Module
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.science_activity.*

class ScienceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.science_activity)

        val dbReference:DatabaseReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference

        dbReference.child("Modules").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    var modulesList: ArrayList<Module> = arrayListOf()

                    for (s in snapshot.children)
                    {
                           var module: Module? = s.getValue(Module::class.java)
                            modulesList.add(module!!)
                    }

                    println("ModuleList: ${modulesList.size}")

                    science_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                    science_recycler_view.adapter = ScienceAdapter(applicationContext, modulesList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })




    }
}