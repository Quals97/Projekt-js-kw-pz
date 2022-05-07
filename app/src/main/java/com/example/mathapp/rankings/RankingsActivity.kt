package com.example.mathapp.rankings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        var users: ArrayList<User> = arrayListOf()
                        var userStatistics: ArrayList<ModuleStats> = arrayListOf()
                        var connectPair = arrayListOf<Pair<User, ArrayList<ModuleStats>>>()

                        for (user in snapshot.children)
                        {
                            val userData = user.getValue(User::class.java)
                            println("USER: ${user}")
                            println("Children: ${user.child("Statistics").value}")

                            if (user.child("Statistics").value != null)
                            {
                                println("==========================================================")
                                println("USER STAT")
                                for(stat in user.child("Statistics").children)
                                {
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
                        for (userS in connectPair)
                        {
                            println("userSFirst: ${userS.first.id} || ${userS.first.email} || ${userS.first.name}")
                            for (i in 0 until userS.second.size)
                            {
                                println("userSecond: ${userS.second[i].id} || ${userS.second[i].moduleName} || ${userS.second[i].points} || ${userS.second[i].numberOfGames} || ${userS.second[i].difficultyLevel}")

                            }


                        }

                        recycler_view_rankings_activity.layoutManager = LinearLayoutManager(applicationContext)
                        recycler_view_rankings_activity.adapter = RankingsAdapter(applicationContext, connectPair)


                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })





    }
}