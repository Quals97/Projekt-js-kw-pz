package com.example.mathapp.challenges.quizfiles

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModulesPassed
import com.example.mathapp.authentication.classes.User
import com.example.mathapp.challenges.classes.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.position_in_category_recycler_view.view.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter (var context: Context, var categoriesList: ArrayList<Category>, val difficultyLevel: Int,
                       val difficultName: String
                       ):RecyclerView.Adapter<CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_category_recycler_view, parent, false)
        return CategoryViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val layout = holder.view.position_in_category_recycler_view
        val categoryName = holder.view.categoryName

        categoryName.text = categoriesList[position].categoryName
        layout.setOnClickListener{

            val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
            db.child("Users")
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            var users: ArrayList<User> = arrayListOf()
                            for (u in snapshot.children)
                            {
                                println("u: ${u.children}")
                                val user = u.getValue(User::class.java)
                                users.add(user!!)
                            }

                            var currentUser: List<User> = users.filter { n->n.id == FirebaseAuth.getInstance().currentUser!!.uid }

                            val intent = Intent(holder.view.context.applicationContext, QuizActivity::class.java)
                            intent.putExtra("difficultyLevel", difficultyLevel)
                            intent.putExtra("position", position)
                            intent.putExtra("difficultName", difficultName)
                            intent.putExtra("currentUserId", currentUser[0].id)
                            intent.putExtra("currentUserName", currentUser[0].name)
                            intent.putExtra("currentUserEmail", currentUser[0].email)
                            intent.putExtra("userSettingsTime", currentUser[0].userSettings.quizSettings.delayTimePerQuestion)

                            holder.view.context.startActivity(intent)


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }


                })



        }

    }

    override fun getItemCount(): Int {
      return categoriesList.size
    }


}




class CategoryViewHolder(val view: View): RecyclerView.ViewHolder(view){}