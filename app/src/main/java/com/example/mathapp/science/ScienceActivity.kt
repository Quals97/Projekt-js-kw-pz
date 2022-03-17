package com.example.mathapp.science

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathapp.R
import kotlinx.android.synthetic.main.science_activity.*

class ScienceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.science_activity)

        science_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        science_recycler_view.adapter = ScienceAdapter(applicationContext)

    }
}