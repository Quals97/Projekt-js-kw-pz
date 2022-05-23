package com.example.mathapp.challenges.quizfiles

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.*
import com.example.mathapp.levels.ExperiencePerLevel
import com.example.mathapp.levels.Level
import com.example.mathapp.science.moduleexam.ModuleExamAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_result_module_exam.*
import kotlinx.android.synthetic.main.quiz_result_activity.*
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class QuizResultActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    val patternOfScoringSystemEasyLevel = 2
    val patternOfScoringSystemDifficultLevel = 3
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

                        if(difficultName == "Łatwy"){

                            val scoringSystem: Int = intent.getIntExtra("points",-1) * patternOfScoringSystemEasyLevel
                            val newUserLevel: Level = ExperiencePerLevel.calculateLevel(level!!, scoringSystem)

                            val dbLevel = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                            dbLevel.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child("level").setValue(newUserLevel)
                        }else if(difficultName == "Trudny"){
                            val scoringSystem: Int = intent.getIntExtra("points",-1) * patternOfScoringSystemDifficultLevel
                            val newUserLevel: Level = ExperiencePerLevel.calculateLevel(level!!, scoringSystem)

                            val dbLevel = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
                            dbLevel.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child("level").setValue(newUserLevel)

                        }


                        goToMainActivity()

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })


    }

    private fun goToMainActivity()
    {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}