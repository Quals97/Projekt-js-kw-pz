package com.example.mathapp.help

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_help1.*
import kotlinx.android.synthetic.main.activity_main.*

class HelpActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help1)

        image_nextPage.setOnClickListener {
            startActivity(Intent(applicationContext, HelpActivity2::class.java))
        }
        image_prevPage.setOnClickListener {
            onBackPressed()
        }


//        img_helpActivity1
        val dbC = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        dbC.child("HelpImages").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (image in snapshot.children){
                        println("IMAGE: ${image}")
                        if (image.key == "page1_2"){
                           // Picasso.get().load(image.value.toString()).into(img_helpActivity1)

                        }else if(image.key == "arrowLeft"){
                            Picasso.get().load(image.value.toString()).into(image_prevPage)
                        }else if(image.key == "arrowRight"){
                            Picasso.get().load(image.value.toString()).into(image_nextPage)
                        }

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}