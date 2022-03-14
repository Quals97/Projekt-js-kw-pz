package com.example.mathapp.authentication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.registration_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)


        //Nasłuchiwanie wprowadzania emaila przez użytkownika
        user_email.addTextChangedListener(emailWatcher)
        //Nasłuchiwanie wprowadzania hasła przez użytkownika
        user_password.addTextChangedListener(passwordWatcher)
        //Nasłuchiwanie wprowadzania imienia przez użytkownika
        user_first_name.addTextChangedListener(firstNameWatcher)

        //Nasłuchiwanie wciśnięcia przycisku 'Zarejestruj'
        registration_button.setOnClickListener(registrationListener)





    }

    private val passwordWatcher: TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val checkBoolean: Boolean = checkUserInputPassword(user_password.text.toString())



                if(checkBoolean)
                {
                    checkInputUserPassword.setTextColor(Color.GREEN)
                    checkInputUserPassword.text = "Success"
                }else if(!checkBoolean){
                    if(user_password.text.toString().isEmpty())
                    {
                        checkInputUserPassword.text = ""
                    }else{
                        checkInputUserPassword.setTextColor(Color.RED)
                        checkInputUserPassword.text = "The password should have between 12 and 64 charakters with one special charakter like [@.,';']"
                    }


                }





        }


    }

    private val firstNameWatcher: TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val checkBoolean: Boolean = checkUserInputFirtName(user_first_name.text.toString())
                if (checkBoolean)
                {
                    checkInputUserFirstName.setTextColor(Color.GREEN)
                    checkInputUserFirstName.text = "Success"
                }else if(!checkBoolean){
                    if(user_first_name.text.toString().isEmpty())
                    {
                        checkInputUserFirstName.text = ""
                    }else{
                        checkInputUserFirstName.setTextColor(Color.RED)
                        checkInputUserFirstName.text = "The name should start with Upper-case without special charakters"
                    }

                }
        }


    }

    private val emailWatcher: TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val checkBoolean: Boolean = checkUserInputEmail(user_email.text.toString())

            if(checkBoolean)
            {
                checkInputUserData.setTextColor(Color.GREEN)
                checkInputUserData.text = "Success"
            }else if(!checkBoolean){
                if (user_email.text.toString().isEmpty())
                {
                    checkInputUserData.text = ""
                }else{
                    checkInputUserData.setTextColor(Color.RED)
                    checkInputUserData.text = "Wrong data format (example.213@gmail.com)"
                }


            }
        }

    }

    private val registrationListener: View.OnClickListener = View.OnClickListener {

        val email: String = user_email.text.toString()
        val password: String = user_password.text.toString()


        if (checkUserInputEmail(user_email.text.toString()) && checkUserInputPassword(user_password.text.toString()) && checkUserInputFirtName(user_first_name.text.toString())
        ){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(){
                        task ->
                    if (task.isSuccessful)
                    {


                        //val firebaseUser: FirebaseUser = task.result!!.user!!



                        Toast.makeText(applicationContext, "Pomyślnie zarejestrowano.", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()


                    }else{
                        Toast.makeText(applicationContext,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }

                }


        }else{
            Toast.makeText(applicationContext, "Błędny format wprowadzonych danych", Toast.LENGTH_LONG).show()
        }




    }

    private fun checkUserInputFirtName(firstName:String): Boolean
    {
        val pattern: Pattern = Pattern.compile("^[A-Z][a-z]+\$")
        val matcher: Matcher = pattern.matcher(firstName)

        return matcher.matches()
    }
    private fun checkUserInputEmail(email:String): Boolean
    {
        val pattern: Pattern = Pattern.compile("^(\\w+)([\\-.'][\\w]+)*@(\\w+)([\\.][\\w]+)*\\.([A-Za-z]){2,6}\$")
        val matcher: Matcher = pattern.matcher(email)

        return matcher.matches()
    }
    private fun checkUserInputPassword(password:String): Boolean
    {
        var check: Boolean = false
        if(password.length in 12..64)
        {
            val pattern: Pattern = Pattern.compile("^(\\w+)([\\p{Punct}\\+\\=\\^\\\$][\\w]+)||([\\w]+[\\p{Punct}\\+\\=\\^\\$])\$")
            val matcher: Matcher = pattern.matcher(password)
            if (matcher.matches())
            {
                check=true
            }
        }else{
            check = false
        }

        //val pattern: Pattern = Pattern.compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#\$%^&+=])(?=\\S+\$).{8,}\$")



        //return matcher.matches()
        return check
    }


}