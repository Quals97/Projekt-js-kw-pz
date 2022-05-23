package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mathapp.challenges.ChallengesActivity
import com.example.mathapp.levels.ExperiencePerLevel
import com.example.mathapp.levels.Level
import com.example.mathapp.rankings.RankingsActivity
import com.example.mathapp.science.ScienceActivity
import com.example.mathapp.science.ScienceAdapter
import com.example.mathapp.useroption.OptionsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    var doubleBackClick: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        study_button.setOnClickListener(scienceListener)
        options_button.setOnClickListener(optionsListener)
        challenge_button.setOnClickListener(challengeListener)
        rankings_button.setOnClickListener(rankingsListener)

        setMenuIcon()
//        println("LANGUAGE: ${Locale.getDefault().displayLanguage}")




        val dbLevel = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        dbLevel.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("level").addListenerForSingleValueEvent(object : ValueEventListener {
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

                        val max = ExperiencePerLevel.experiencePerLevel[level.level.toInt()+1]-1
                        level_main_activity.text = level.level
                        experience_main_activity.text = "${level.points}/${max}"
                        progress_bar_experience_main_activity.max = max - ExperiencePerLevel.experiencePerLevel[level.level.toInt()]
                        val currentProgress = max - level.points.toInt()

                        progress_bar_experience_main_activity.progress =
                            (max - ExperiencePerLevel.experiencePerLevel[level.level.toInt()]) - currentProgress




                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })




    }


    override fun onBackPressed() {
        if (doubleBackClick)
        {
            finishAffinity()

        }
        if(!doubleBackClick)
        {
            Toast.makeText(applicationContext,"Tap two times to close app", Toast.LENGTH_SHORT).show()
        }
        doubleBackClick = true

        Timer("Set_value_false", false).schedule(1000){
            doubleBackClick = false
        }


    }
    private val rankingsListener: View.OnClickListener = View.OnClickListener {
        goToRankingsActivity()
    }

    private val challengeListener: View.OnClickListener = View.OnClickListener {
        goToChallengeActivity()
    }

    private val scienceListener: View.OnClickListener = View.OnClickListener {
        goToScienceActivity()
    }

    private val optionsListener: View.OnClickListener = View.OnClickListener {
        goToOptionsActivity()
    }
    private fun goToRankingsActivity(){
        val intent = Intent(applicationContext, RankingsActivity::class.java)
        startActivity(intent)
    }

    private fun goToChallengeActivity(){
        val intent = Intent(applicationContext, ChallengesActivity::class.java)
        startActivity(intent)
    }

    private fun goToScienceActivity(){
        val intent = Intent(applicationContext,ScienceActivity::class.java)
        startActivity(intent)

    }

    private fun goToOptionsActivity(){
        val intent = Intent(applicationContext,OptionsActivity::class.java)
        startActivity(intent)

    }


    private fun setMenuIcon(){
//        Picasso.get().load(appInfor!!.logo).into(image_logo)
//        sciencie_icon
        val dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        dbC.child("MenuIcons")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {

                        for (icon in snapshot.children)
                        {

                            when(Locale.getDefault().displayLanguage.toString()){
                                "English"-> {
                                    if (icon.key == "scienceEng"){
                                        println("SCIENCE LOGO")
                                        Picasso.get().load(icon.value.toString()).into(sciencie_icon)
                                    }else if(icon.key == "challengeEng"){
                                        Picasso.get().load(icon.value.toString()).into(challenge_icon)
                                    }else if(icon.key == "rankingEngPl"){
                                        Picasso.get().load(icon.value.toString()).into(rankings_icon)
                                    }else if(icon.key == "settingsEng"){
                                        Picasso.get().load(icon.value.toString()).into(settings_icon)
                                    }


                                }
                                "polski" -> {
                                    if (icon.key == "sciencePl"){
                                        Picasso.get().load(icon.value.toString()).into(sciencie_icon)
                                    }else if(icon.key == "challengePl"){
                                        Picasso.get().load(icon.value.toString()).into(challenge_icon)
                                    }else if(icon.key == "rankingEngPl"){
                                        Picasso.get().load(icon.value.toString()).into(rankings_icon)
                                    }else if(icon.key == "settingsPl"){
                                        Picasso.get().load(icon.value.toString()).into(settings_icon)
                                    }

                                }

                            }


//                            if (icon.key == "science"){
//                                println("SCIENCE LOGO")
//                                Picasso.get().load(icon.value.toString()).into(sciencie_icon)
//                            }else if(icon.key == "challenge"){
//                                Picasso.get().load(icon.value.toString()).into(challenge_icon)
//                            }else if(icon.key == "rankings"){
//                                Picasso.get().load(icon.value.toString()).into(rankings_icon)
//                            }else if(icon.key == "settings"){
//                                Picasso.get().load(icon.value.toString()).into(settings_icon)
//                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })


    }
}