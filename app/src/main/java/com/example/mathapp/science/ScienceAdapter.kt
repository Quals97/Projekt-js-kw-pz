package com.example.mathapp.science

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModulesPassed
import com.example.mathapp.science.classes.Module
import com.example.mathapp.science.modules.ModulesActivity
import kotlinx.android.synthetic.main.position_in_sience_recycler_view.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class ScienceAdapter(val context: Context, val modulesList:ArrayList<Module>, val modulesPassed: ArrayList<ModulesPassed>): RecyclerView.Adapter<MySienceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_sience_recycler_view, parent, false)
        return MySienceViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: MySienceViewHolder, position: Int) {
        val moduleTitle = holder.view.module_title
        val layout = holder.view.position_in_sience_recycler_view

        println("SIZE MODULES: ${modulesPassed.size}")
        moduleTitle.text = modulesList[position].name

        layout.setBackgroundResource(R.drawable.roundup)


        var drawable: GradientDrawable = layout.background as GradientDrawable

        if (position < modulesPassed.size)
        {
            println("ModulesPassedScienceAdapter: ${ modulesPassed[position].id}")
            println("ModulesPassedScienceAdapter: ${ modulesPassed[position].name}")
            println("ModulesPassedScienceAdapter: ${ modulesPassed[position].status}")
            println("ModulesPassedScienceAdapter: ${ modulesPassed[position].points}")
        }


        layout.setOnClickListener{

            moduleTitle.setTextColor(Color.parseColor("#095E50"))



            Timer("module_click", false).schedule(250)
            {

                moduleTitle.setTextColor(Color.WHITE)



                if (position < modulesPassed.size)
                {
                    var id = modulesPassed[position].id
                    var points = modulesPassed[position].points
                    var status = modulesPassed[position].status
                    var name = modulesPassed[position].name
                    val intent = Intent(holder.view.context.applicationContext, ModulesActivity::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("idModulesPassed", id)
                    intent.putExtra("pointsModulesPassed", points)
                    intent.putExtra("statusModulesPassed", status)
                    intent.putExtra("nameModulesPassed", name)
                    holder.view.context.startActivity(intent)
                }else{
                    val intent = Intent(holder.view.context.applicationContext, ModulesActivity::class.java)
                    intent.putExtra("position", position)
                    holder.view.context.startActivity(intent)
                }



            }






        }

        if (position < modulesPassed.size)
        {
            if (modulesPassed[position].status.toBoolean())
            {
                drawable.setColor(Color.parseColor("#CD038350"))
            }else{
                drawable.setColor(Color.GRAY)
            }
        }else{
            drawable.setColor(Color.GRAY)
        }
//           println("Module name ${modulesPassed[position]?.status}")





        if (position % 2 == 1)
        {      //drawable.setColor(Color.parseColor("#E4731B"))
          //  layout.setBackgroundColor(Color.parseColor("#E4731B"))
        }
        if(position % 2 ==0)
        {  // drawable.setColor(Color.parseColor("#ffbd59"))
           // layout.setBackgroundColor(Color.parseColor("#ffbd59"))
        }
    }

    override fun getItemCount(): Int {
        return modulesList.size
        //return FakeModulesList.modulesList.size
    }

}

class MySienceViewHolder(val view: View): RecyclerView.ViewHolder(view){}