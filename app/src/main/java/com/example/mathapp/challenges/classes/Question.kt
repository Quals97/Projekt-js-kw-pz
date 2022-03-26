package com.example.mathapp.challenges.classes

import com.example.mathapp.challenges.classes.Answers

class Question {
    var id:String = "0"
    var title: String = String()
    var answers: Answers = Answers()

    constructor()

    constructor(id: String, title:String, answers: Answers)
    {
        this.id = id
        this.title = title
        this.answers = answers
    }
}