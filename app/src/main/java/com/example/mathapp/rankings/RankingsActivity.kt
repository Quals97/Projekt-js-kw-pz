package com.example.mathapp.rankings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModuleStats
import com.example.mathapp.authentication.classes.User
import com.example.mathapp.science.classes.Module
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_rankings.*
import kotlin.time.Duration.Companion.seconds

class RankingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rankings)

        val spinner = findViewById<Spinner>(R.id.spinner_ranks)
        val items = arrayListOf<String>("Ogółem", "Moduły")
        val spinnerAdapter = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )

        spinner.adapter = spinnerAdapter

        val spinnerModules = findViewById<Spinner>(R.id.spinner_ranks_modules)
        val itemsModules = arrayListOf<String>("Liczby naturalne", "Zbiory", "Przybliżenia")
        val spinnerModulesAdapter = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            itemsModules
        )

        spinnerModules.adapter = spinnerModulesAdapter

        val spinnerDifficulty = findViewById<Spinner>(R.id.spinner_ranks_difficulty)
        val itemsDifficulty = arrayListOf<String>("Łatwy", "Trudny")
        val spinnerDifficultyAdapter = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            itemsDifficulty
        )

        spinnerDifficulty.adapter = spinnerDifficultyAdapter



        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var users: ArrayList<User> = arrayListOf()
                        var userStatistics: ArrayList<ModuleStats> = arrayListOf()
                        var connectPair = arrayListOf<Pair<User, ArrayList<ModuleStats>>>()

                        for (user in snapshot.children) {
                            val userData = user.getValue(User::class.java)
                            println("USER: ${user}")
                            println("Children: ${user.child("Statistics").value}")

                            if (user.child("Statistics").value != null) {
                                println("==========================================================")
                                println("USER STAT")
                                for(stat in user.child("Statistics").children) {
                                    val userStat: ModuleStats? = stat.getValue(ModuleStats::class.java)
                                    println("stat: ${stat}")
                                    userStatistics.add(userStat!!)


                                }
                                println("==========================================================")
                                connectPair.add(Pair(userData!!, userStatistics))
                                userStatistics = arrayListOf()
                            }else{
                                connectPair.add(Pair(userData!!, arrayListOf<ModuleStats>()))
                            }

                            users.add(userData!!)

                        }





                        println("Connect Pair Size: ${connectPair.size}")

                        println("UserStatistics: ${userStatistics.size}")
                        for (userS in connectPair){
                            println("userSFirst: ${userS.first.id} || ${userS.first.email} || ${userS.first.name}")
                            for (i in 0 until userS.second.size)
                            {
                                println("userSecond: ${userS.second[i].id} || ${userS.second[i].moduleName} || ${userS.second[i].points} || ${userS.second[i].numberOfGames} || ${userS.second[i].difficultyLevel}")

                            }


                        }

                        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {



                                val items = spinner.selectedItem.toString()

                                when(items){
                                    "Ogółem" -> {
                                        spinnerDifficulty.visibility = View.GONE
                                        spinnerModules.visibility = View.GONE
                                        println("Wybrano ogółem.")
                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Ogółem", String(), String())

                                    }
                                    "Moduły" -> {


                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", "Liczby naturalne", "Łatwy")
                                        spinnerDifficulty.setSelection(0)
                                        spinnerModules.setSelection(0)

                                        spinnerDifficulty.visibility = View.VISIBLE
                                        spinnerModules.visibility = View.VISIBLE
                                        spinnerModules.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {






                                                val itemsModules = spinnerModules.selectedItem.toString()
                                                when(itemsModules){
                                                    "Liczby naturalne" -> {

                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, "Łatwy")
                                                        spinnerDifficulty.setSelection(0)

                                                        spinnerDifficulty.visibility = View.VISIBLE
                                                        spinnerDifficulty.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                                                            override fun onItemSelected(
                                                                parent: AdapterView<*>?,
                                                                view: View?,
                                                                position: Int,
                                                                id: Long
                                                            ) {

                                                                var itemsDiffictulty = spinnerDifficulty.selectedItem.toString()

                                                                when(itemsDiffictulty){
                                                                    "Łatwy" -> {
                                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, itemsDiffictulty)

                                                                    }
                                                                    "Trudny" -> {

                                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, itemsDiffictulty)
                                                                    }

                                                                }

                                                            }

                                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                                            }

                                                        }



                                                        println("${itemsModules}")

                                                    }
                                                    "Zbiory" -> {

                                                        spinnerDifficulty.visibility = View.VISIBLE

                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, "Łatwy")
                                                        spinnerDifficulty.setSelection(0)

                                                        spinnerDifficulty.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                                                            override fun onItemSelected(
                                                                parent: AdapterView<*>?,
                                                                view: View?,
                                                                position: Int,
                                                                id: Long
                                                            ) {
                                                                var itemsDiffictulty = spinnerDifficulty.selectedItem.toString()
                                                                when(itemsDiffictulty){
                                                                    "Łatwy" -> {
                                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, itemsDiffictulty)


                                                                    }
                                                                    "Trudny" -> {

                                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, itemsDiffictulty)

                                                                    }

                                                                }


                                                            }

                                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                                            }


                                                        }

                                                        println("${itemsModules}")


                                                    }
                                                    "Przybliżenia" -> {
                                                        println("${itemsModules}")
                                                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                                                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Moduły", itemsModules, String())


                                                    }


                                                }






                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                            }

                                        }




//                                        val layout = LinearLayout(applicationContext)
//
//                                        var spinnerArray = arrayListOf<String>(
//                                            "Liczby naturalne",
//                                            "Zbiory",
//                                            "Przybliżenia"
//                                        )
//                                        var spinner = Spinner(applicationContext)
//                                        var spinnerArrayAdapter = ArrayAdapter<String>(
//                                            applicationContext,
//                                            android.R.layout.simple_spinner_dropdown_item,
//                                            spinnerArray
//                                        )
//                                        spinner.adapter = spinnerArrayAdapter
//                                        layout.addView(spinner)
//                                        setContentView(layout)

//                                        connectPair = arrayListOf()

                                        println("Wybrano Moduły.")

                                    }
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }
                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair, "Ogółem", String(), String())







                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })





    }
}