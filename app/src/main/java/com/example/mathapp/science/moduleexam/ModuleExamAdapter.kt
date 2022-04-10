package com.example.mathapp.science.moduleexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import kotlinx.android.synthetic.main.position_in_module_exam_adapter.view.*

class ModuleExamAdapter (var context: Context, var category: Category):RecyclerView.Adapter<MyModuleExamAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyModuleExamAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_module_exam_adapter, parent, false)
        return MyModuleExamAdapter(positionList)
    }

    override fun onBindViewHolder(holder: MyModuleExamAdapter, position: Int) {
        var questionTitle = holder.view.questionTitleModuleExamActivity
        var answer1 = holder.view.questionAnswer1ModuleExamActivity
        var answer2 = holder.view.questionAnswer2ModuleExamActivity
        var answer3 = holder.view.questionAnswer3ModuleExamActivity
        var answer4 = holder.view.questionAnswer4ModuleExamActivity

        questionTitle.text = "${category.questions0[position].title}"
        answer1.text = "${category.questions0[position].answers.answer1.title}"
        answer2.text = "${category.questions0[position].answers.answer2.title}"
        answer3.text = "${category.questions0[position].answers.answer3.title}"
        answer4.text = "${category.questions0[position].answers.answer4.title}"
    }

    override fun getItemCount(): Int {
        return category.questions0.size
    }


}



class MyModuleExamAdapter(val view: View): RecyclerView.ViewHolder(view){}





