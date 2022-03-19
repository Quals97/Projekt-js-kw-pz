package com.example.mathapp.useroption

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathapp.R
import com.example.mathapp.authentication.LoginActivity
import com.example.mathapp.useroption.changepassword.ChangePasswordActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.options_activity.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_activity)

        change_password_button.setOnClickListener(changePasswordListener)

        logout_button.setOnClickListener(logOutListener)
    }

    private val changePasswordListener = View.OnClickListener {
        goToChangePasswordActivity()

    }

    private val logOutListener = View.OnClickListener {
        logOut()

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