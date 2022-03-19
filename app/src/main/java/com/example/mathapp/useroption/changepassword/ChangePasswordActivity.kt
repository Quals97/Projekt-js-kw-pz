package com.example.mathapp.useroption.changepassword

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mathapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.change_password_activity.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password_activity)


        change_password_button.setOnClickListener(changePasswordListener)

        change_password_edit_text.addTextChangedListener(changePasswordWatcher)

        show_new_password.setOnClickListener(showNewPasswordListener)

    }


    private val changePasswordWatcher = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val checkBoolean: Boolean = checkUserInputPassword(change_password_edit_text.text.toString())
            println("check boolean: ${checkBoolean}")
            if (checkBoolean)
            {
                check_new_password.setTextColor(Color.parseColor("#E4731B"))
                check_new_password.text = "Success"



            }else if(!checkBoolean){

                if (change_password_edit_text.text.toString().isEmpty())
                {
                    check_new_password.text = ""

                }else{

                    check_new_password.setTextColor(Color.RED)
                    check_new_password.text = "Hasło powinno zawierać od 12 do 64 znaków w tym jeden znak specjalny taki jak [@.,';]"
                }


            }

        }


    }

    private val showNewPasswordListener = View.OnClickListener {
        if (change_password_edit_text.transformationMethod == PasswordTransformationMethod.getInstance())
        {
            change_password_edit_text.transformationMethod = HideReturnsTransformationMethod.getInstance()


            show_new_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24_1)
        }else
        {
            change_password_edit_text.transformationMethod = PasswordTransformationMethod.getInstance()

            show_new_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
        }

//        if (change_password_edit_text.inputType == 129)
//        {
//            change_password_edit_text.inputType  = InputType.TYPE_CLASS_TEXT
//            show_new_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24_1)
//        }else
//        {
//            change_password_edit_text.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            show_new_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
//        }


    }

    private val changePasswordListener = View.OnClickListener {

        val user = FirebaseAuth.getInstance().currentUser
        val newPassword = change_password_edit_text.text.toString()

        if (newPassword.isNotEmpty())
        {
            if (checkUserInputPassword(change_password_edit_text.text.toString()))
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

            }else{
                Toast.makeText(applicationContext, "Niepoprawny format danych.", Toast.LENGTH_SHORT).show()
            }

        }else if(newPassword.isEmpty())
        {
            check_new_password.text = ""
            Toast.makeText(applicationContext, "Pole nie może być puste.", Toast.LENGTH_SHORT).show()
        }



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