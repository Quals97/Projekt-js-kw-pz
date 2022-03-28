package com.example.mathapp.science.modules

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.example.mathapp.R
import com.example.mathapp.science.classes.Module
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_example.*
import java.lang.reflect.Type

class ModulesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modules_activity)

        val modulePosition: Int = intent.getIntExtra("position",-1)
        val dbReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        var db = dbReference.child("Modules")
        var modules: ArrayList<Module> = arrayListOf()
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for (m in snapshot.children) {
                        var module: Module? = m.getValue(Module::class.java)
                        modules.add(module!!)

                    }
                    val moduleTitle = TextView(applicationContext)
                    moduleTitle.setTextColor(Color.WHITE)
                    moduleTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0F)
                    moduleTitle.text = modules[modulePosition].name
                    layout_add.addView(moduleTitle)

                    for (m in 0 until modules[modulePosition].paragraph.size)
                    {
                        if (modules[modulePosition].paragraph[m].viewType == "text")
                        {
                            val text = TextView(applicationContext)
                            text.setTextColor(Color.WHITE)
                            text.text = modules[modulePosition].paragraph[m].text
                            layout_add.addView(text)

                        }else if (modules[modulePosition].paragraph[m].viewType == "image")
                        {
                            val link: String = modules[modulePosition].paragraph[m].imageLocalization!!.localization
                            var imageView = ImageView(applicationContext)
                            layout_add.addView(imageView)
                            Picasso.get().load(link).into(imageView)
                        }

                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}