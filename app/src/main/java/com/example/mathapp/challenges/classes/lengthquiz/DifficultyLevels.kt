package com.example.mathapp.challenges.classes.lengthquiz

class DifficultyLevels {
    var id: String = String()
    var difficultyLevels: ArrayList<LengthOfTheQuiz> = arrayListOf()

    constructor()

    constructor(id: String, difficultyLevels: ArrayList<LengthOfTheQuiz>)
    {
        this.id = id
        this.difficultyLevels = difficultyLevels
    }
}