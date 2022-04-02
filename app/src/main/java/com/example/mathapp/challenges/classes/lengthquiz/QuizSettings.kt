package com.example.mathapp.challenges.classes.lengthquiz

import android.graphics.Color

class QuizSettings {
    var id: String = String()
    //var difficultyLevels: DifficultyLevels = DifficultyLevels()
    var difficultyLevels: ArrayList<LengthOfTheQuiz> = arrayListOf()
    var textColor : String = String()

    constructor()

    constructor(id: String, difficultyLevels: ArrayList<LengthOfTheQuiz>, textColor: String)
    {
        this.id = id
        this.difficultyLevels = difficultyLevels
        this.textColor = textColor
    }
}