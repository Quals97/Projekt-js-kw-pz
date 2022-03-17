package com.example.mathapp.useroption.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.change_password_activity.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password_activity)

        change_password_button.setOnClickListener(changePasswordListener)

    }

    private val changePasswordListener = View.OnClickListener {

        val user = FirebaseAuth.getInstance().currentUser
        val newPassword = change_password_edit_text.text.toString()

        if (newPassword.isNotEmpty())
        {
            user!!.updatePassword(newPassword).addOnCompleteListener{
                if (it.isSuccessful)
                {
                    Toast.makeText(applicationContext, "Zmieniono hasło.", Toast.LENGTH_SHORT).show()
                    onBackPressed()

                }else{
                    Toast.makeText(applicationContext, "Nie udało się zmienić hasła.", Toast.LENGTH_SHORT).show()

                }
            }

        }else if(newPassword.isEmpty())
        {
            Toast.makeText(applicationContext, "Pole nie może być puste.", Toast.LENGTH_SHORT).show()
        }


    }


}