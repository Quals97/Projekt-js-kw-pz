package com.example.mathapp.useroption

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathapp.R
import com.example.mathapp.authentication.LoginActivity
import com.example.mathapp.authentication.classes.QuizSettings
import com.example.mathapp.authentication.classes.UserSettings
import com.example.mathapp.useroption.changepassword.ChangePasswordActivity
import com.example.mathapp.useroption.quizsettings.QuizSettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.options_activity.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_activity)

        change_password_button.setOnClickListener(changePasswordListener)
        quiz_settings_button.setOnClickListener(quizSettingsListener)
        logout_button.setOnClickListener(logOutListener)
    }

    private val quizSettingsListener = View.OnClickListener {
        goToQuizSettingsActivity()
    }

    private val changePasswordListener = View.OnClickListener {
        goToChangePasswordActivity()

    }

    private val logOutListener = View.OnClickListener {
        logOut()

    }
    private fun goToQuizSettingsActivity(){
        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists())
                   {
                       var userSettings: UserSettings = UserSettings()
                       for (s in snapshot.children)
                       {
                           if (s.key == "userSettings")
                           {
                               userSettings = s.getValue(UserSettings::class.java)!!

                           }
                       }

                       val intent = Intent(applicationContext, QuizSettingsActivity::class.java)
                       intent.putExtra("userSettingsDelayTime", userSettings.quizSettings.delayTimePerQuestion)
                       startActivity(intent)
                   }
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })




    }

    private fun goToChangePasswordActivity(){
        val intent = Intent(applicationContext, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun logOut()
    {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, LoginActivity::class.java))

    }
}