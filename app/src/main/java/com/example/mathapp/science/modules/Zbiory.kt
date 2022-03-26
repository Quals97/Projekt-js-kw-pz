package com.example.mathapp.science.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathapp.R
import com.example.mathapp.science.classes.Module
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.position_in_sience_recycler_view.*
import kotlinx.android.synthetic.main.zbiory_activity.*

class Zbiory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zbiory_activity)



        val dbReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        var db = dbReference.child("Modules")
        var modules: ArrayList<Module> = arrayListOf()
        db.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for (m in snapshot.children) {
                        var module: Module? = m.getValue(Module::class.java)
                        println("${module}")
                        modules.add(module!!)

                    }

                    zbiory_module_title.text = modules[0].name

                    modele_paragraph1.text = modules[0].paragraph[0].text
                    modele_paragraph2.text = modules[0].paragraph[1].text
                    modele_paragraph3.text = modules[0].paragraph[3].text
                    modele_paragraph4.text = modules[0].paragraph[4].text
                    modele_paragraph5.text = modules[0].paragraph[5].text
                    modele_paragraph6.text = modules[0].paragraph[6].text
                    modele_paragraph7.text = modules[0].paragraph[7].text
                    modele_paragraph8.text = modules[0].paragraph[8].text
                    modele_paragraph9.text = modules[0].paragraph[9].text
                    modele_paragraph10.text = modules[0].paragraph[10].text
                    modele_paragraph11.text = modules[0].paragraph[11].text
                    modele_paragraph12.text = modules[0].paragraph[12].text

                    // module_title.text = modules[0].id


                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })




    }
}