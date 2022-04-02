package com.example.mathapp.challenges.quizfiles

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import kotlinx.android.synthetic.main.position_in_category_recycler_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter (var context: Context, var categoriesList: ArrayList<Category>, val difficultyLevel: Int):RecyclerView.Adapter<CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_category_recycler_view, parent, false)
        return CategoryViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val layout = holder.view.position_in_category_recycler_view
        val categoryName = holder.view.categoryName

        categoryName.text = categoriesList[position].categoryName
        layout.setOnClickListener{
            val intent = Intent(holder.view.context.applicationContext, QuizActivity::class.java)
            intent.putExtra("difficultyLevel", difficultyLevel)
            intent.putExtra("position", position)
            holder.view.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
      return categoriesList.size
    }


}




class CategoryViewHolder(val view: View): RecyclerView.ViewHolder(view){}