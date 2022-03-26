package com.example.mathapp.challenges.classes

import com.example.mathapp.challenges.classes.Answer

class Answers {
    var answer1: Answer = Answer()
    var answer2: Answer = Answer()
    var answer3: Answer = Answer()
    var answer4: Answer = Answer()
    var correctAnswer: Answer = Answer()

    constructor(answer1: Answer, answer2: Answer, answer3: Answer,
                answer4: Answer, correctAnswer: Answer)
    {
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.answer4 = answer4
        this.correctAnswer = correctAnswer

    }

    constructor()

}