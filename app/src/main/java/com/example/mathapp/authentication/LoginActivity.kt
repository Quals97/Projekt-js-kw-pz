package com.example.mathapp.authentication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.exifinterface.media.ExifInterface
import com.example.mathapp.LogoTextApp
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

import com.google.firebase.database.ktx.database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.user_email
import kotlinx.android.synthetic.main.activity_login.user_password
import kotlinx.android.synthetic.main.registration_activity.*
import kotlinx.android.synthetic.main.start_activity.*
import org.w3c.dom.Text
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.concurrent.schedule


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        user_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        var query = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference

        query.child("AppInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists())
                {

                    var appInfor = LogoTextApp()

                    for (ai in snapshot.children)
                    {

                        if (ai.key == "logo")
                        {
                            appInfor.logo = ai.value.toString()
                        }else if(ai.key == "text")
                        {
                            appInfor.text = ai.value.toString()
                        }

                        //val appInfor = ai.getValue(LogoTextApp::class.java)!!

                    }

//




                    Picasso.get().load(appInfor.logo).into(image_logo_login_activity)

                }


                //var link: String? = snapshot.getValue(String::class.java)

            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

        user_email.addTextChangedListener(emailWatcher)

        login_button.setOnClickListener(loginListener)

        //forgot_password.setOnClickListener(forgotPasswordListener)

        registration.setOnClickListener(registrationListener)

        show_password.setOnClickListener(showPasswordListener)


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
                checkUserEmail.setTextColor(Color.parseColor("#E4731B"))
                checkUserEmail.text = "${applicationContext.getString(R.string.correct_input)}"
            }else if(!checkBoolean)
            {
                if (user_email.text.toString().isEmpty())
                {
                    checkUserEmail.text = ""
                }else{
                    checkUserEmail.setTextColor(Color.RED)
                    checkUserEmail.text = "${applicationContext.getString(R.string.wrong_data_format)}"
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
                checkUserPassword.text = "${applicationContext.getString(R.string.correct_input)}"
            }else if(!checkBoolean)
            {
                checkUserPassword.setTextColor(Color.RED)
                checkUserPassword.text = ""
            }
        }


    }

    private val showPasswordListener = View.OnClickListener {

        if (user_password.transformationMethod == PasswordTransformationMethod.getInstance())
        {
            user_password.transformationMethod = HideReturnsTransformationMethod.getInstance()


            show_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24_1)
        }else
        {
            user_password.transformationMethod = PasswordTransformationMethod.getInstance()

            show_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
        }


    }

    private val registrationListener: View.OnClickListener = View.OnClickListener {
        val intent: Intent = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intent)

    }

    private val forgotPasswordListener = View.OnClickListener {
        val intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private val loginListener = View.OnClickListener {
//        val email = "kamilwin21@gmail.com"
//        val password = "123456789abc;"
        val email: String = user_email.text.toString()
        val password: String = user_password.text.toString()
//====================================================
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
//            .addOnCompleteListener(){ task ->
//
//                if(task.isSuccessful){
//
//                    val database = Firebase.database
//                    val myRef = database.getReference("message")
//
//                    myRef.setValue("Hello, World!")
//
//                    Toast.makeText(applicationContext,
//                        applicationContext.getString(R.string.success_login_in),
//                        Toast.LENGTH_SHORT).show()
//
//                    val intent = Intent(applicationContext, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
//                    intent.putExtra("email_id", email)
//                    startActivity(intent)
//                    finish()
//
//
//                }else{
//                    if (task.exception!!.message == "The password is invalid or the user does not have a password." ||
//                        task.exception!!.message == "There is no user record corresponding to this identifier. The user may have been deleted.")
//                    {
//                        Toast.makeText(applicationContext, "${applicationContext.getString(R.string.invalid_login_password)}", Toast.LENGTH_LONG).show()
//                    }
//
//
//
//
//                }
//
//            }
//============================================================================


        if(user_email.text.toString().isNotEmpty() && user_password.text.toString().isNotEmpty() && checkInputUserEmail(user_email.text.toString()) && checkInputUserPassword(user_password.text.toString()))
        {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(){ task ->

                    if(task.isSuccessful){

                        val database = Firebase.database
                        val myRef = database.getReference("message")

                        myRef.setValue("Hello, World!")

                        Toast.makeText(applicationContext,
                            applicationContext.getString(R.string.success_login_in),
                            Toast.LENGTH_SHORT).show()

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()


                    }else{
                        if (task.exception!!.message == "The password is invalid or the user does not have a password." ||
                            task.exception!!.message == "There is no user record corresponding to this identifier. The user may have been deleted.")
                        {
                            Toast.makeText(applicationContext, "${applicationContext.getString(R.string.invalid_login_password)}", Toast.LENGTH_LONG).show()
                        }




                    }

                }


        }else if(user_email.text.toString().isNotEmpty() && user_password.text.toString().isNotEmpty()){
            Toast.makeText(applicationContext, applicationContext.getString(R.string.wrong_data), Toast.LENGTH_LONG).show()

        }else if(user_email.text.toString().isEmpty() || user_password.text.toString().isEmpty())
        {
            //Toast.makeText(applicationContext, "Pola nie mogą być puste", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "${applicationContext.getString(R.string.empty_fields)}", Toast.LENGTH_LONG).show()
        }




    }

    private fun checkInputUserPassword(userPassword: String): Boolean{
        val pattern: Pattern = Pattern.compile("^(\\w+)([\\p{Punct}\\+\\=\\^\\$][\\w]+)||([\\w]+[\\p{Punct}\\+\\=\\^\\\$])\$")
        val matcher: Matcher = pattern.matcher(userPassword)

        return matcher.matches()
    }

    private fun checkInputUserEmail(userEmail:String):Boolean{
        val pattern: Pattern = Pattern.compile("^(\\w+)([\\-.'][\\w]+)*@(\\w+)([\\.][\\w]+)*\\.([A-Za-z]){2,6}\$")
        val matcher: Matcher = pattern.matcher(userEmail)

        return matcher.matches()
    }





}