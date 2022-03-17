package com.example.mathapp.science

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import kotlinx.android.synthetic.main.position_in_sience_recycler_view.view.*

class ScienceAdapter(val context: Context): RecyclerView.Adapter<MySienceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_sience_recycler_view, parent, false)
        return MySienceViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: MySienceViewHolder, position: Int) {
        val moduleTitle = holder.view.module_title
        moduleTitle.text = "Module ${position}"
    }

    override fun getItemCount(): Int {
        return 5
    }

}

class MySienceViewHolder(val view: View): RecyclerView.ViewHolder(view){}