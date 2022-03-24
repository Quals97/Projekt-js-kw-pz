package com.example.mathapp.authentication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.registration_activity.*
import kotlinx.android.synthetic.main.registration_activity.user_email
import kotlinx.android.synthetic.main.registration_activity.user_password
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

        repeat_user_password.addTextChangedListener(reapeatPassword)

        //Nasłuchiwanie wprowadzania imienia przez użytkownika
        user_first_name.addTextChangedListener(firstNameWatcher)

        //Nasłuchiwanie wciśnięcia przycisku 'Zarejestruj'
        registration_button.setOnClickListener(registrationListener)

        show_password_registration.setOnClickListener(showPasswordListener)

        var query = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference

        query.child("logo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var link: String? = snapshot.getValue(String::class.java)
                Picasso.get().load(link).into(image_logo_registration_activity)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })


    }





    private val reapeatPassword = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val checkBoolean: Boolean = checkUserInputPassword(repeat_user_password.text.toString())
            if(checkBoolean)
            {

                if(repeat_user_password.text.toString() == user_password.text.toString())
                {
                    checkInputUserRepeatPassword.setTextColor(Color.parseColor("#E4731B"))
                    checkInputUserRepeatPassword.text = "${applicationContext.getString(R.string.correct_input)}"
                }else{
                    checkInputUserRepeatPassword.setTextColor(Color.RED)
                    checkInputUserRepeatPassword.text = "${applicationContext.getString(R.string.match_passwords)}"
                }

            }else if(!checkBoolean){
                if (repeat_user_password.text.toString().isEmpty())
                {
                    checkInputUserRepeatPassword.text = ""
                }else{
                    checkInputUserRepeatPassword.setTextColor(Color.RED)
                    //checkInputUserRepeatPassword.text = "The password should have between 12 and 64 charakters with one special charakter like [@.,';]"
                    checkInputUserRepeatPassword.text = "${applicationContext.getString(R.string.password_validation)}"
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

            val checkBoolean: Boolean = checkUserInputPassword(user_password.text.toString())

            if(checkBoolean)
                {
                    checkInputUserPassword.setTextColor(Color.parseColor("#E4731B"))
                    checkInputUserPassword.text = "${applicationContext.getString(R.string.correct_input)}"
                    repeat_user_password.isEnabled = true
                }else if(!checkBoolean){
                    repeat_user_password.isEnabled = false
                    if(user_password.text.toString().isEmpty())
                    {
                        checkInputUserPassword.text = ""
                    }else{
                        checkInputUserPassword.setTextColor(Color.RED)
                        checkInputUserPassword.text = "${applicationContext.getString(R.string.password_validation)}"
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
                    checkInputUserFirstName.setTextColor(Color.parseColor("#E4731B"))
                    checkInputUserFirstName.text = "${applicationContext.getString(R.string.correct_input)}"
                }else if(!checkBoolean){
                    if(user_first_name.text.toString().isEmpty())
                    {
                        checkInputUserFirstName.text = ""
                    }else{
                        checkInputUserFirstName.setTextColor(Color.RED)
                        checkInputUserFirstName.text = "${applicationContext.getString(R.string.name_validation)}"
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
                checkInputUserData.setTextColor(Color.parseColor("#E4731B"))
                checkInputUserData.text = "${applicationContext.getString(R.string.correct_input)}"
            }else if(!checkBoolean){
                if (user_email.text.toString().isEmpty())
                {
                    checkInputUserData.text = ""
                }else{
                    checkInputUserData.setTextColor(Color.RED)
                    checkInputUserData.text = "${applicationContext.getString(R.string.wrong_data_format)}"
                }


            }
        }

    }
    //Zmiana hasła na tekst jawny/ tekst ukryty
    private val showPasswordListener = View.OnClickListener {

        if (user_password.transformationMethod == PasswordTransformationMethod.getInstance())
        {
            user_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            repeat_user_password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            show_password_registration.setImageResource(R.drawable.ic_baseline_remove_red_eye_24_1)
        }else
        {
            user_password.transformationMethod = PasswordTransformationMethod.getInstance()
            repeat_user_password.transformationMethod = PasswordTransformationMethod.getInstance()
            show_password_registration.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
        }

    }

    private val registrationListener: View.OnClickListener = View.OnClickListener {

        val email: String = user_email.text.toString()
        val password: String = user_password.text.toString()


        if (checkUserInputEmail(user_email.text.toString()) && checkUserInputPassword(user_password.text.toString()) && checkUserInputPassword(repeat_user_password.text.toString())  && checkUserInputFirtName(user_first_name.text.toString())
                    && checkInputUserRepeatPassword.text.toString() != "${applicationContext.getString(R.string.match_passwords)}"
        ){
            if (user_password.text.toString() == repeat_user_password.text.toString())
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){
                            task ->
                        if (task.isSuccessful)
                        {

                            Toast.makeText(applicationContext, "${applicationContext.getString(R.string.successfully_registered)}", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()


                        }else{
                            if (task.exception!!.message == "The email address is already in use by another account.")
                            {
                                Toast.makeText(applicationContext, "${applicationContext.getString(R.string.email_already_used)}", Toast.LENGTH_LONG).show()
                            }

                        }

                    }

            }else{
                Toast.makeText(applicationContext,"${applicationContext.getString(R.string.match_passwords)}",Toast.LENGTH_SHORT).show()
            }



        }else if(user_email.text.toString().isEmpty() || user_password.text.toString().isEmpty() || user_first_name.text.toString().isEmpty() || repeat_user_password.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "${applicationContext.getString(R.string.fields_must_be_completed)}", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(applicationContext, "${applicationContext.getString(R.string.wrong_data_format1)}", Toast.LENGTH_LONG).show()
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

        return check
    }


}