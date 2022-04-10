package com.example.mathapp.challenges.classes

import com.example.mathapp.challenges.classes.Answers

class Question {
    var id:String = "0"
    var title: String = String()
    var answers: Answers = Answers()
    var difficulty: String = String()

    constructor()

    constructor(id: String, title:String, answers: Answers, difficulty: String)
    {
        this.id = id
        this.title = title
        this.answers = answers
        this.difficulty = difficulty
    }
}