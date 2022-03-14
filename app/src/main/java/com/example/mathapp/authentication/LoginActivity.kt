package com.example.mathapp.authentication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

import com.google.firebase.database.ktx.database
import kotlinx.android.synthetic.main.activity_login.*
import org.w3c.dom.Text
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.concurrent.schedule


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        user_email.addTextChangedListener(emailWatcher)

        login_button.setOnClickListener(loginListener)

        registration.setOnClickListener(registrationListener)


    }



    override fun onBackPressed() {


        moveTaskToBack(false)
        //super.onBackPressed()
    }



    private val emailWatcher: TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {


            val checkBoolean: Boolean = checkInputUserEmail(user_email.text.toString())
            if (checkBoolean)
            {
                checkUserEmail.setTextColor(Color.GREEN)
                checkUserEmail.text = "Success"
            }else if(!checkBoolean)
            {
                if (user_email.text.toString().isEmpty())
                {
                    checkUserEmail.text = ""
                }else{
                    checkUserEmail.setTextColor(Color.RED)
                    checkUserEmail.text = "Wrong data format (example213@gmail.com)"
                }

            }
        }


    }

    private val passwordWatcher: TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val checkBoolean: Boolean = checkInputUserPassword(user_password.text.toString())


            if(checkBoolean)
            {
                checkUserPassword.setTextColor(Color.GREEN)
                checkUserPassword.text = "Success"
            }else if(!checkBoolean)
            {
                checkUserPassword.setTextColor(Color.RED)
                checkUserPassword.text = ""
            }
        }


    }

    private val registrationListener: View.OnClickListener = View.OnClickListener {
        val intent: Intent = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intent)

    }

    private val loginListener = View.OnClickListener {
//        val email = "kamilwin21@gmail.com"
//        val password = "12345678"
        val email: String = user_email.text.toString()
        val password: String = user_password.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful){
                    val database = Firebase.database
                    val myRef = database.getReference("message")

                    myRef.setValue("Hello, World!")

                    Toast.makeText(applicationContext,
                        "Pomyślnie zalogowano",
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(applicationContext,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT).show()

                }

            }


    }

    private fun checkInputUserPassword(userPassword: String): Boolean{
        val pattern: Pattern = Pattern.compile("^(\\w+)([\\p{Punct}][\\w]+)\$")
        val matcher: Matcher = pattern.matcher(userPassword)

        return matcher.matches()
    }
    private fun checkInputUserEmail(userEmail:String):Boolean{
        val pattern: Pattern = Pattern.compile("^(\\w+)([\\-.'][\\w]+)*@(\\w+)([\\.][\\w]+)*\\.([A-Za-z]){2,6}\$")
        val matcher: Matcher = pattern.matcher(userEmail)

        return matcher.matches()
    }



}