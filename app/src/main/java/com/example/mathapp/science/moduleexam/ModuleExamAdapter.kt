package com.example.mathapp.science.moduleexam

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.MainActivity
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModulesPassed
import com.example.mathapp.challenges.classes.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.module_exam_activity.*
import kotlinx.android.synthetic.main.module_exam_activity.view.*
import kotlinx.android.synthetic.main.position_in_module_exam_adapter.view.*
import kotlinx.android.synthetic.main.quiz_activity.view.*
import java.util.*
import kotlin.collections.ArrayList

class ModuleExamAdapter (var context: Context, var category: Category, val checkAnswerButton: Button,
                         var userAnswers: ArrayList<Pair<String, String>> = arrayListOf(),
                         var modulesPassed: ModulesPassed?
                         ):RecyclerView.Adapter<MyModuleExamAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyModuleExamAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_module_exam_adapter, parent, false)
        return MyModuleExamAdapter(positionList)
    }

    override fun onBindViewHolder(holder: MyModuleExamAdapter, position: Int) {
        val percentToPass = 60
        val questionsSize: Int = category.questions0.size

        val layout = holder.view.position_in_module_exam_adapter
        var questionTitle = holder.view.questionTitleModuleExamActivity
        var answer1 = holder.view.questionAnswer1ModuleExamActivity
        var answer2 = holder.view.questionAnswer2ModuleExamActivity
        var answer3 = holder.view.questionAnswer3ModuleExamActivity
        var answer4 = holder.view.questionAnswer4ModuleExamActivity









        questionTitle.text = "${position+1}. ${category.questions0[position].title}"
        answer1.text = "${category.questions0[position].answers.answer1.title}"
        answer2.text = "${category.questions0[position].answers.answer2.title}"
        answer3.text = "${category.questions0[position].answers.answer3.title}"
        answer4.text = "${category.questions0[position].answers.answer4.title}"

        var id = category.questions0[position].id


//        for (i in userAnswers)
//        {
//            println("UserAnswers: ${i.first} : ${i.second}")
//        }

        if(modulesPassed == null)
       {
           if (category.questions0[position].answers.correctAnswer.title == userAnswers[position].second)
           {
               if (answer1.text == userAnswers[position].second)
               {
                   answer1.setBackgroundColor(Color.GREEN)

               }else if (answer2.text == userAnswers[position].second)
               {
                   answer2.setBackgroundColor(Color.GREEN)

               }else if (answer3.text == userAnswers[position].second){
                   answer3.setBackgroundColor(Color.GREEN)

               }else if (answer4.text == userAnswers[position].second){
                   answer4.setBackgroundColor(Color.GREEN)

               }
           }else if (category.questions0[position].answers.correctAnswer.title != userAnswers[position].second)
           {
               if (answer1.text == userAnswers[position].second)
               {
                   answer1.setBackgroundColor(Color.RED)
                   if (answer2.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer2.setBackgroundColor(Color.GREEN)
                   }else if (answer3.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer3.setBackgroundColor(Color.GREEN)
                   }else if (answer4.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer4.setBackgroundColor(Color.GREEN)
                   }

               }else if (answer2.text == userAnswers[position].second)
               {
                   answer2.setBackgroundColor(Color.RED)
                   if (answer1.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer1.setBackgroundColor(Color.GREEN)
                   }else if (answer3.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer3.setBackgroundColor(Color.GREEN)
                   }else if (answer4.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer4.setBackgroundColor(Color.GREEN)
                   }

               }else if (answer3.text == userAnswers[position].second){
                   answer3.setBackgroundColor(Color.RED)
                   if (answer1.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer1.setBackgroundColor(Color.GREEN)
                   }else if (answer2.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer2.setBackgroundColor(Color.GREEN)
                   }else if (answer4.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer4.setBackgroundColor(Color.GREEN)
                   }

               }else if (answer4.text == userAnswers[position].second){
                   answer4.setBackgroundColor(Color.RED)
                   if (answer1.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer1.setBackgroundColor(Color.GREEN)
                   }else if (answer2.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer2.setBackgroundColor(Color.GREEN)
                   }else if (answer3.text == category.questions0[position].answers.correctAnswer.title)
                   {
                       answer3.setBackgroundColor(Color.GREEN)
                   }

               }
           }

//            if (answer1.text == userAnswers[position].second)
//            {
//                answer1.setBackgroundColor(Color.GREEN)
//            }else{
//                if (answer2.text == userAnswers[position].second)
//                {
//                    answer1.setBackgroundColor(Color.GREEN)
//                    answer2.setBackgroundColor(Color.RED)
//                }else if(answer3.text == userAnswers[position].second)
//                {
//                    answer1.setBackgroundColor(Color.GREEN)
//                    answer3.setBackgroundColor(Color.RED)
//                }else if(answer4.text == userAnswers[position].second)
//                {
//                    answer1.setBackgroundColor(Color.GREEN)
//                    answer4.setBackgroundColor(Color.RED)
//                }
//            }


//            if (answer2.text.toString() == category.questions0[position].answers.correctAnswer.title)
//            {
//               answer2.setBackgroundColor(Color.GREEN)
//            }
//            if (answer3.text.toString() == category.questions0[position].answers.correctAnswer.title)
//            {
//               answer3.setBackgroundColor(Color.GREEN)
//            }
//            if (answer4.text.toString() == category.questions0[position].answers.correctAnswer.title)
//            {
//               answer4.setBackgroundColor(Color.GREEN)
//            }


       }else{







           answer1.setOnClickListener {

               setXmlOnButton(answer1, answer2, answer3, answer4)

               userAnswers[position] = Pair(category.questions0[position].id,answer1.text.toString())
               println("size: ${userAnswers.size}")

           }
           answer2.setOnClickListener {

               setXmlOnButton(answer2, answer1, answer3, answer4)


               userAnswers[position] = Pair(category.questions0[position].id,answer2.text.toString())



           }
           answer3.setOnClickListener {

               setXmlOnButton(answer3, answer2, answer1, answer4)


               userAnswers[position] = Pair(category.questions0[position].id,answer3.text.toString())



           }
           answer4.setOnClickListener {

               setXmlOnButton( answer4, answer3, answer2, answer1)

               userAnswers[position] = Pair(category.questions0[position].id,answer4.text.toString())

           }
       }

        checkAnswerButton.setOnClickListener {


            var correctAnswers = category.questions0.filter { n -> n.id == userAnswers[n.id.toInt()].first && n.answers.correctAnswer.title == userAnswers.get(n.id.toInt()).second }




            var check: Boolean = false

            check = (correctAnswers.size*100)/questionsSize >= percentToPass
            //check = correctAnswers.size >= 3

            val time = Date().time
            val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference


            if (modulesPassed == null)
            {
                val intent = Intent(holder.view.context.applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                holder.view.context.startActivity(intent)

            }else{
                if (modulesPassed!!.id == "null" && modulesPassed!!.name == "null")
                {

                    val modulePassed = ModulesPassed(time.toString(), category.categoryName, check.toString(), correctAnswers.size.toString())
                    db.child("Users").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("ModulesPassed").child(time.toString()).setValue(modulePassed)

                }else{
                    if (correctAnswers.size > modulesPassed!!.points.toInt())
                    {
                        val modulePassed = ModulesPassed(modulesPassed!!.id, category.categoryName, check.toString(), correctAnswers.size.toString())
                        db.child("Users").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("ModulesPassed").child(modulesPassed!!.id).setValue(modulePassed)

                    }

                }
                var extra = Bundle()
                extra.putSerializable("userAnswer", userAnswers)

                val intent = Intent(holder.view.context.applicationContext, ResultModuleExamActivity::class.java)
                intent.putExtra("categoryName", category.categoryName)
                intent.putExtra("userAnswerExtra", extra)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                holder.view.context.startActivity(intent)
            }



//            val intent = Intent(holder.view.context.applicationContext, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            holder.view.context.startActivity(intent)



        }
    }

    override fun getItemCount(): Int {
        return category.questions0.size
    }


}


private fun setXmlOnButton(answer1: TextView, answer2: TextView, answer3: TextView, answer4: TextView){
    answer1.setTypeface(null, Typeface.BOLD)
    answer1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
    answer2.setTypeface(null, Typeface.NORMAL)
    answer3.setTypeface(null, Typeface.NORMAL)
    answer4.setTypeface(null, Typeface.NORMAL)

    answer2.setBackgroundResource(R.drawable.round_answer_module_exam_adapter)
    answer3.setBackgroundResource(R.drawable.round_answer_module_exam_adapter)
    answer4.setBackgroundResource(R.drawable.round_answer_module_exam_adapter)
    answer1.setBackgroundResource(R.drawable.round_answer_module_exam_adapter_color)

}



class MyModuleExamAdapter(val view: View): RecyclerView.ViewHolder(view){}





