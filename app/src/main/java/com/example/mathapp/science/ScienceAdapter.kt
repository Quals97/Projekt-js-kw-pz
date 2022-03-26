package com.example.mathapp.science

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.FakeModulesList
import com.example.mathapp.R
import com.example.mathapp.science.classes.Module
import com.example.mathapp.science.modules.Zbiory
import kotlinx.android.synthetic.main.position_in_sience_recycler_view.view.*
import java.io.PipedOutputStream

class ScienceAdapter(val context: Context, val modulesList:ArrayList<Module>): RecyclerView.Adapter<MySienceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_sience_recycler_view, parent, false)
        return MySienceViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: MySienceViewHolder, position: Int) {
        val moduleTitle = holder.view.module_title
        val layout = holder.view.position_in_sience_recycler_view

        moduleTitle.text = modulesList[position].name

        layout.setBackgroundResource(R.drawable.roundup)

        var drawable: GradientDrawable = layout.background as GradientDrawable

        layout.setOnClickListener{
            when(position)
            {
                0 -> {
                    val intent = Intent(holder.view.context.applicationContext, Zbiory::class.java)
                    holder.view.context.startActivity(intent)
                }

            }

        }


        if (position % 2 == 1)
        {      drawable.setColor(Color.parseColor("#E4731B"))
          //  layout.setBackgroundColor(Color.parseColor("#E4731B"))
        }
        if(position % 2 ==0)
        {   drawable.setColor(Color.parseColor("#ffbd59"))
           // layout.setBackgroundColor(Color.parseColor("#ffbd59"))
        }
    }

    override fun getItemCount(): Int {
        return modulesList.size
        //return FakeModulesList.modulesList.size
    }

}

class MySienceViewHolder(val view: View): RecyclerView.ViewHolder(view){}