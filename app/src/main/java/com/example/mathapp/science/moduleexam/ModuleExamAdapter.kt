package com.example.mathapp.science.moduleexam

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.challenges.classes.Category
import kotlinx.android.synthetic.main.module_exam_activity.view.*
import kotlinx.android.synthetic.main.position_in_module_exam_adapter.view.*

class ModuleExamAdapter (var context: Context, var category: Category, val checkAnswerButton: Button,
                         var userAnswers: ArrayList<Pair<String, String>> = arrayListOf()
                         ):RecyclerView.Adapter<MyModuleExamAdapter>(){
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

        var id = category.questions0[position].id





        answer1.setOnClickListener {



                answer2.setBackgroundColor(Color.parseColor("#878783"))
                answer3.setBackgroundColor(Color.parseColor("#878783"))
                answer4.setBackgroundColor(Color.parseColor("#878783"))
                it.setBackgroundColor(Color.parseColor("#E4731B"))

            userAnswers[position] = Pair(category.questions0[position].id,answer1.text.toString())
            println("size: ${userAnswers.size}")

        }
        answer2.setOnClickListener {

            answer1.setBackgroundColor(Color.parseColor("#878783"))
            answer3.setBackgroundColor(Color.parseColor("#878783"))
            answer4.setBackgroundColor(Color.parseColor("#878783"))
            it.setBackgroundColor(Color.parseColor("#E4731B"))
            userAnswers[position] = Pair(category.questions0[position].id,answer2.text.toString())



        }
        answer3.setOnClickListener {
            answer1.setBackgroundColor(Color.parseColor("#878783"))
            answer2.setBackgroundColor(Color.parseColor("#878783"))
            answer4.setBackgroundColor(Color.parseColor("#878783"))
            it.setBackgroundColor(Color.parseColor("#E4731B"))
            userAnswers[position] = Pair(category.questions0[position].id,answer3.text.toString())



        }
        answer4.setOnClickListener {
            answer1.setBackgroundColor(Color.parseColor("#878783"))
            answer2.setBackgroundColor(Color.parseColor("#878783"))
            answer3.setBackgroundColor(Color.parseColor("#878783"))
            it.setBackgroundColor(Color.parseColor("#E4731B"))
            userAnswers[position] = Pair(category.questions0[position].id,answer4.text.toString())

        }

        checkAnswerButton.setOnClickListener {
            for (n in userAnswers)
            {
                println("KEY: ${n.first} : VALUE ${n.second}")
            }
            println("LIST SIZE: ${userAnswers.size}")
            var correctAnswers = category.questions0.filter { n -> n.id == userAnswers[n.id.toInt()].first && n.answers.correctAnswer.title == userAnswers.get(n.id.toInt()).second }
            println("correctAnswers: ${correctAnswers.size}")
            for (n in correctAnswers)
            {
                println("======================================")
                println("${n.id}")
                println("${n.title}")
                println("${n.answers.answer1.title}")
                println("${n.answers.answer2.title}")
                println("${n.answers.answer3.title}")
                println("${n.answers.answer4.title}")
                println("======================================")

            }
            println("RESULT: ${category.questions0.filter { n -> n.id == userAnswers[n.id.toInt()].first && n.answers.correctAnswer.title == userAnswers[n.id.toInt()].second }
            }")


        }
    }

    override fun getItemCount(): Int {
        return category.questions0.size
    }


}



class MyModuleExamAdapter(val view: View): RecyclerView.ViewHolder(view){}





