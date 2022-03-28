package com.example.mathapp.challenges.classes

class Category {

    var id: String = String()
    var categoryName: String = String()
    var questions: ArrayList<Question> = arrayListOf()

    constructor()

    constructor(id: String, categoryName: String, questions: ArrayList<Question>)
    {
        this.id = id
        this.categoryName = categoryName
        this.questions = questions
    }

}