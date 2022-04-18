package com.example.mathapp.authentication.classes

class UserSettings {
    var quizSettings: QuizSettings = QuizSettings()

    constructor(quizSettings: QuizSettings)
    {
        this.quizSettings = quizSettings
    }

    constructor()
}