package com.example.mathapp.science.modules

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.mathapp.R
import com.example.mathapp.science.classes.Module
import com.example.mathapp.science.moduleexam.ModuleExamActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_example.*
import org.w3c.dom.Text
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
                   // moduleTitle.setTextColor(Color.WHITE)
                    moduleTitle.setTextColor(Color.parseColor("#E4731B"))
                    moduleTitle.gravity = Gravity.CENTER
                    moduleTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.0F)
                    moduleTitle.text = modules[modulePosition].name
                    layout_add.addView(moduleTitle)

                    for (s in 0 until modules[modulePosition].sections.size)
                    {



                        val sectionName = TextView(applicationContext)
                        //sectionName.setTextColor(Color.WHITE)
                        sectionName.setTextColor(Color.parseColor("#E4731B"))
                        sectionName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0F)
                        sectionName.text = modules[modulePosition].sections[s].name
                        if(sectionName.text.toString().isBlank())
                        {
                            sectionName.height = 10
                        }
                        layout_add.addView(sectionName)
                        for (p in 0 until modules[modulePosition].sections[s].paragraphs.size)
                        {

                            if (modules[modulePosition].sections[s].paragraphs[p].viewType == "text")
                            {
                                val text = TextView(applicationContext)
                                text.setTextColor(Color.WHITE)
                                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0F)
                                text.text = modules[modulePosition].sections[s].paragraphs[p].text
                                layout_add.addView(text)

                            }else if (modules[modulePosition].sections[s].paragraphs[p].viewType == "image")
                            {
                                val link: String = modules[modulePosition].sections[s].paragraphs[p].imageLocalization!!.localization
                                var imageView = ImageView(applicationContext)
                                layout_add.addView(imageView)
                                Picasso.get().load(link).into(imageView)
                            }


                            }




                    }
                    var examButton = Button(applicationContext)
                    examButton.text = applicationContext.getString(R.string.Test)
                    examButton.setBackgroundColor(Color.parseColor("#E4731B"))
                    examButton.setTextColor(Color.WHITE)

                    val idModulesPassed = intent.getStringExtra("idModulesPassed")
                    val pointsModulesPassed = intent.getStringExtra("pointsModulesPassed")
                    val statusModulesPassed = intent.getStringExtra("statusModulesPassed")
                    val nameModulesPassed = intent.getStringExtra("nameModulesPassed")
                    println("ModulesPassedModulesActivityid: ${idModulesPassed}")
                    println("ModulesPassedModulesActivityPoints: ${pointsModulesPassed}")
                    println("ModulesPassedModulesActivityStatus: ${statusModulesPassed}")
                    println("ModulesPassedModulesActivityName: ${nameModulesPassed}")



                    layout_add.addView(examButton)


                    examButton.setOnClickListener{
                        var intent = Intent(applicationContext, ModuleExamActivity::class.java)
                        intent.putExtra("idModule", modules[modulePosition].id)
                        intent.putExtra("categoryName", modules[modulePosition].name)
                        intent.putExtra("idModulesPassed", idModulesPassed)
                        intent.putExtra("pointsModulesPassed", pointsModulesPassed)
                        intent.putExtra("statusModulesPassed", statusModulesPassed)
                        intent.putExtra("nameModulesPassed", nameModulesPassed)
                        startActivity(intent)

                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}