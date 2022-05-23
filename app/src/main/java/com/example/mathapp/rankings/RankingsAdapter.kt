package com.example.mathapp.rankings

import android.content.Context
import android.os.Build
import android.text.BoringLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp.R
import com.example.mathapp.authentication.classes.ModuleStats
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.position_in_rankings_recycler_view.view.*

class RankingsAdapter(val context: Context,
                      val users: ArrayList<Pair<com.example.mathapp.authentication.classes.User, ArrayList<ModuleStats>>>,
                      val ItemClicked: String,
                      val ModuleClicked: String,
                      val itemDifficulty: String
                      ):RecyclerView.Adapter<MyRankingsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRankingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.position_in_rankings_recycler_view, parent, false)
        return MyRankingsViewHolder(positionList)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MyRankingsViewHolder, position: Int) {
        val userName = holder.view.rankings_user_email
        val userPoints = holder.view.rankings_user_points
        val userPosition = holder.view.rankings_user_position

        var userPointsList: ArrayList<Pair<String, Int>> = arrayListOf()

        when(position)
        {
            0->{
                userName.textSize = 32F
                userPoints.textSize = 32F
                userPosition.textSize = 32F

            }
            1->{
                userName.textSize = 25F
                userPoints.textSize = 25F
                userPosition.textSize = 25F

            }
            2->{
                userName.textSize = 23F
                userPoints.textSize = 23F
                userPosition.textSize = 23F

            }


        }

        when(ItemClicked){
            "Ogółem" -> {
                for (i in 0 until users.size) {
                    var pair = Pair(users[i].first.email, 0)
                    for (j in 0 until users[i].second.size)
                    {
                        pair = Pair(pair.first, pair.second.plus(users[i].second[j].points.toInt()))


                    }
                    if(pair.second != 0){
                        userPointsList.add(pair)
                    }

                }


//        var helpList = userPointsList.sortedWith(compareBy{it.second})
                var helpList = userPointsList.sortedBy { it.second }.reversed()

                userPointsList = arrayListOf()

                for (i in helpList) {
                    userPointsList.add(i)
                }

                for (i in userPointsList) {
                    println("USER POINTS LIST: ${i.first} || ${i.second}")
                }
                println("SIZE: ${userPointsList.size}")


                if (position < userPointsList.size)
                {
                    userName.text = userPointsList[position].first
                    userPoints.text = userPointsList[position].second.toString()
                    userPosition.text = position.plus(1).toString()
                }


            }
            "Moduły" -> {

                when(ModuleClicked)
                {
                    "Liczby naturalne" -> {
                            var sortedListOfQuestionForUser = arrayListOf<Pair<String, Int>>()

                    for (i in 0 until users.size) {

                        var pair = Pair(users[i].first.email, 0)
                        var check: Boolean = false
                        for (k in 0 until users[i].second.size) {

                            when(itemDifficulty){
                                "Łatwy" -> {
                                    if (users[i].second[k].moduleName == "Liczby naturalne" && users[i].second[k].difficultyLevel == "Łatwy")
                                    {

                                        if (users[i].second[k].points.toInt() != 0){
                                            sortedListOfQuestionForUser.add(Pair(users[i].first.email,users[i].second[k].points.toInt()))
                                            check = true
                                            println("Łatwy: ${Pair(users[i].first.email,users[i].second[k].points.toInt())}")
                                        }


                                    }
                                }
                                "Trudny" -> {

                                    if (users[i].second[k].moduleName == "Liczby naturalne" && users[i].second[k].difficultyLevel == "Trudny")
                                    {

                                        if (users[i].second[k].points.toInt() != 0){
                                            sortedListOfQuestionForUser.add(Pair(users[i].first.email,users[i].second[k].points.toInt()))
                                            check = true
                                            println("Trudny: ${Pair(users[i].first.email,users[i].second[k].points.toInt())}")
                                        }

                                    }
                                }
                            }


                        }
                        if (!check)
                        {
                           // sortedListOfQuestionForUser.add(Pair(users[i].first.email, 0))
                        }

                    }
                        var helpList = sortedListOfQuestionForUser.sortedBy { u -> u.second }.reversed()
                    sortedListOfQuestionForUser = arrayListOf()

                        for (h in 0 until helpList.size) {
                        sortedListOfQuestionForUser.add(helpList[h])

                    }

                        if (position < sortedListOfQuestionForUser.size)
                        {
                            userName.text = sortedListOfQuestionForUser[position].first
                            userPoints.text = sortedListOfQuestionForUser[position].second.toString()
                            userPosition.text = position.plus(1).toString()
                        }






                }
                    "Zbiory" -> {
                        var sortedListOfQuestionForUser = arrayListOf<Pair<String, Int>>()

                        for (i in 0 until users.size) {

                            var pair = Pair(users[i].first.email, 0)
                            var check: Boolean = false
                            for (k in 0 until users[i].second.size)
                            {

                                when(itemDifficulty){
                                    "Łatwy" -> {
                                        if (users[i].second[k].moduleName == "Zbiory" && users[i].second[k].difficultyLevel == "Łatwy")
                                        {
                                            if (users[i].second[k].points.toInt() != 0){
                                                sortedListOfQuestionForUser.add(Pair(users[i].first.email,users[i].second[k].points.toInt()))
                                                check = true
                                                println("Łatwy: ${Pair(users[i].first.email,users[i].second[k].points.toInt())}")

                                            }


                                        }

                                    }
                                    "Trudny" -> {

                                        if (users[i].second[k].moduleName == "Zbiory" && users[i].second[k].difficultyLevel == "Trudny")
                                        {
                                            if (users[i].second[k].points.toInt() != 0){
                                                sortedListOfQuestionForUser.add(Pair(users[i].first.email,users[i].second[k].points.toInt()))
                                                check = true
                                                println("Trudny: ${Pair(users[i].first.email,users[i].second[k].points.toInt())}")

                                            }


                                        }
                                    }

                                }



                            }
                            if (!check)
                            {
                               // sortedListOfQuestionForUser.add(Pair(users[i].first.email, 0))
                            }

                        }
                        var helpList = sortedListOfQuestionForUser.sortedBy { u -> u.second }.reversed()
                        sortedListOfQuestionForUser = arrayListOf()

                        for (h in 0 until helpList.size) {
                            sortedListOfQuestionForUser.add(helpList[h])

                        }

                        if (position < sortedListOfQuestionForUser.size) {
                            userName.text = sortedListOfQuestionForUser[position].first
                            userPoints.text = sortedListOfQuestionForUser[position].second.toString()
                            userPosition.text = position.plus(1).toString()
                        }




                    }
                    "Przybliżenia" -> {
                        var sortedListOfQuestionForUser = arrayListOf<Pair<String, Int>>()

                        for (i in 0 until users.size) {

                            var pair = Pair(users[i].first.email, 0)
                            var check: Boolean = false
                            for (k in 0 until users[i].second.size)
                            {
                                if (users[i].second[k].moduleName == "Przybliżenia" && users[i].second[k].difficultyLevel == "Łatwy")
                                {
                                    sortedListOfQuestionForUser.add(Pair(users[i].first.email,users[i].second[k].points.toInt()))
                                    check = true

                                }

                            }
                            if (!check)
                            {
                                sortedListOfQuestionForUser.add(Pair(users[i].first.email, 0))
                            }

                        }
                        var helpList = sortedListOfQuestionForUser.sortedBy { u -> u.second }.reversed()
                        sortedListOfQuestionForUser = arrayListOf()

                        for (h in 0 until helpList.size) {
                            sortedListOfQuestionForUser.add(helpList[h])

                        }

                        userName.text = sortedListOfQuestionForUser[position].first
                        userPoints.text = sortedListOfQuestionForUser[position].second.toString()
                        userPosition.text = position.plus(1).toString()

                    }
                }



            }


        }




    }

    override fun getItemCount(): Int {
        return users.size

    }


}





class MyRankingsViewHolder(val view: View): RecyclerView.ViewHolder(view){}