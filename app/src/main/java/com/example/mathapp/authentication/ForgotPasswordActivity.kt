package com.example.mathapp.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.forgot_password_activity.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_activity)

        forgot_password_button.setOnClickListener(myForgotPasswordListener)

    }


    private val myForgotPasswordListener = View.OnClickListener {

            if (forgot_password.text.toString().isEmpty())
            {

                Toast.makeText(applicationContext, "Pole nie może być puste.", Toast.LENGTH_LONG).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgot_password.text.toString())
                    .addOnCompleteListener{
                        if (it.isSuccessful)
                        {
                            Toast.makeText(applicationContext, "Pomyślnie wysłano email do zmiany hasła", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }

            }


    }


}