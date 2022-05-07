package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.quiz_result_activity.*
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class QuizResultActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_result_activity)

        var difficultyLevel = intent.getIntExtra("difficultyLevel", -1)
        var difficultName = intent.getStringExtra("difficultName")
        var categoryId: String? = intent.getStringExtra("categoryId")
        var dbConnection = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app//").reference



        result_points_received.text = "${intent.getIntExtra("points",-1).toString()}/${intent.getIntExtra("totalPoints",-1)}"
        println("spentAllTime: ${intent.getIntExtra("spentTime",-1)}")
        progress_bar_points_scored.max = intent.getIntExtra("totalPoints",-1)
        progress_bar_points_scored.progress = intent.getIntExtra("points",-1)

        difficultyName(difficultyLevel_result_activity, difficultName!!)
        spentTime(time_spent_result_activity, intent.getIntExtra("spentTime",-1))



        summary_result.text = "${intent.getStringExtra("categoryName")}"
        result_btn.setOnClickListener(buttonListener)


    }

    private fun difficultyName(textView: TextView, difficultName: String){
        textView.text = difficultName


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun spentTime(textView: TextView, timeSpent: Int){

        var localTime: LocalTime = LocalTime.of(0,0,0)
        localTime.plusSeconds(timeSpent.toLong())
        if (timeSpent < 60)
        {
            textView.text = "${timeSpent} ${applicationContext.getString(R.string.second)}"
        }else if(timeSpent ==60)
        {
            textView.text = "${timeSpent/60} ${applicationContext.getString(R.string.minute)}"
        }else if(timeSpent >60)
        {
            textView.text = "${timeSpent/60} ${applicationContext.getString(R.string.minute)} ${timeSpent%60} ${applicationContext.getString(R.string.second)}"
        }

//        if (localTime.minute == 0)
        //textView.text = timeSpent.toString()

    }

    private val buttonListener = View.OnClickListener {

        var categoryId: String? = intent.getStringExtra("categoryId")
        var categoryName =intent.getStringExtra("categoryName")
        var difficultName = intent.getStringExtra("difficultName")
        println("CategoryId: ${categoryId}")

//        var db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
//        db.child("UserStatistics").child(FirebaseAuth.getInstance().currentUser!!.uid)
//            .addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        var dayStats: ArrayList<DayStats> = arrayListOf()
//                        for (uS in snapshot.children) {
//                            println("uS: ${uS}")
//                            var dayStat = uS.getValue(DayStats::class.java)
//                            dayStats.add(dayStat!!)
//
//                        }
//                        println("DayStats Size: ${dayStats.size}")
//                        for (d in dayStats)
//                        {
//                            println("DayStats: ${d.day} - ${d.month} - ${d.year} || ${d.difficultyLevel}")
//                            for (m in d.moduleStats)
//                            {
//                                println("Modules: ${m.numberOfGames} ${m.points} ${m.moduleName} ${m.id}")
//                            }
//                        }
//
//
//
////                        var moduleStats: ModuleStats = ModuleStats(categoryId!!, categoryName!!, "2", "1")
////
////                        var helpList: ArrayList<ModuleStats> = dayStats[1].moduleStats
////                        helpList.add(moduleStats)
//
//
////                        var dayStatsUser = DayStats("22", "06", "2022",difficultName!!, helpList)
////                        var dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
////                        dbC.child("UserStatistics").child(FirebaseAuth.getInstance().currentUser!!.uid)
////                            .child("${dayStatsUser.day}-${dayStatsUser.month}-${dayStatsUser.year}")
////                            .setValue(dayStats)
//
//
//
//
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })
        var timeId = Date().time

        var dbConnectionStatistics = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        dbConnectionStatistics.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Statistics")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        var modulesStats: ArrayList<ModuleStats> = arrayListOf()
                        for (stat in snapshot.children)
                        {
//                            println("Stat: ${stat}")
                            var moduleStats = stat.getValue(ModuleStats::class.java)
                            modulesStats.add(moduleStats!!)
                        }
//                        println("ModulesSize: ${modulesStats.size}")



//                        var dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
//                        dbC.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
//                            .child("Statistics").child(categoryId).setValue(moduleStats)





                        var modulesStatsList = modulesStats.filter { m -> m.moduleName == categoryName &&
                                                m.difficultyLevel == difficultName }

                        if (modulesStatsList.isEmpty()) // == 0
                        {
                            //Save in database when there is value but the value is no in range of questions module.
                            println("POSZŁO 1")
                            var moduleStats = ModuleStats(timeId.toString(), categoryName!!,
                                "${intent.getIntExtra("points",-1)}", "1", difficultName!!)

                            var dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                            dbC.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child("Statistics").child(timeId.toString()).setValue(moduleStats)
                        }else{
                            //Update value in databese when value there is, but the value is in range of question modules.

                            println("POSZŁO 2")
                            var numberOfGames: Int = modulesStatsList[0].numberOfGames.toInt()
                            var points: Int = modulesStatsList[0].points.toInt()
                            var newPoints: Int = intent.getIntExtra("points",-1) + points
                            var moduleStats = ModuleStats(modulesStatsList[0].id, categoryName!!,
                                newPoints.toString(), (numberOfGames + 1).toString(), difficultName!!)

                            var dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                            dbC.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child("Statistics").child(modulesStatsList[0].id).setValue(moduleStats)

                        }







                    }else{
                        println("POSZŁO 0")
                        //Save in database when there is no value of user statistics
                        var moduleStats = ModuleStats(timeId.toString(), categoryName!!, "${intent.getIntExtra("points",-1)}", "1", difficultName!!)

                        var dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                        dbC.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .child("Statistics").child(timeId.toString()).setValue(moduleStats)


                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })








        goToMainActivity()

    }

    private fun goToMainActivity()
    {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}