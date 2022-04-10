package com.example.mathapp.challenges.classes

class Category {

    var id: String = String()
    var categoryName: String = String()
    var questions: ArrayList<Question> = arrayListOf()
    var questions1: ArrayList<Question> = arrayListOf()
    var questions2: ArrayList<Question> = arrayListOf()
    var questions0: ArrayList<Question> = arrayListOf()


    constructor()

    constructor(id: String, categoryName: String, questions: ArrayList<Question>, questions1: ArrayList<Question>, questions2: ArrayList<Question>
                    , questions0: ArrayList<Question>)
    {
        this.id = id
        this.categoryName = categoryName
        this.questions = questions
        this.questions1 = questions1
        this.questions2 = questions2
        this.questions0 = questions0
    }

}