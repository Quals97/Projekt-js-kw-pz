package com.example.mathapp.challenges

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.lengthquiz.QuizSettings
import com.example.mathapp.challenges.quizfiles.CategoryActivity
import com.example.mathapp.challenges.quizfiles.CategoryAdapter
import com.example.mathapp.challenges.quizfiles.QuizActivity
import kotlinx.android.synthetic.main.position_in_difficulty_levels_recycler_view.view.*

class DifficutyLevelsAdapter(val context: Context, val diffucultyLevelsList: ArrayList<QuizSettings>): RecyclerView.Adapter<MyDifficultyLevelsViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyDifficultyLevelsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_difficulty_levels_recycler_view, parent, false)
        return MyDifficultyLevelsViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: MyDifficultyLevelsViewHolder, position: Int) {
        val layout = holder.view.position_in_difficulty_levels_recycler_view
        val name = holder.view.difficultyLevelsTitle
        val description = holder.view.difficultyLevelsDescription

        name.text = diffucultyLevelsList[0].difficultyLevels[position].name
        name.setTextColor(diffucultyLevelsList[0].textColor.toInt())
        description.text = diffucultyLevelsList[0].difficultyLevels[position].description


        layout.setOnClickListener{
            val intent = Intent(holder.view.context.applicationContext, CategoryActivity::class.java)
            intent.putExtra("difficultyLevel", diffucultyLevelsList[0].difficultyLevels[position].value)
            intent.putExtra("difficultName", diffucultyLevelsList[0].difficultyLevels[position].name)
            holder.view.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return diffucultyLevelsList[0].difficultyLevels.size
    }

}




class MyDifficultyLevelsViewHolder(val view: View): RecyclerView.ViewHolder(view){}