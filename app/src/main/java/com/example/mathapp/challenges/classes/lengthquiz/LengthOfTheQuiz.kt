package com.example.mathapp.challenges.classes.lengthquiz

class LengthOfTheQuiz {
    var id: String = String()
    var name: String = String()
    var description: String = String()
    var value: String = "0"

    constructor()

    constructor(id: String, name: String, description: String, value:String)
    {
        this.id = id
        this.name = name
        this.description = description
        this.value = value
    }

}