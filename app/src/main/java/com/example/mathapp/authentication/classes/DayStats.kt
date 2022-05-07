package com.example.mathapp.authentication.classes

class DayStats {
    var day: String = String()
    var month: String = String()
    var year: String = String()
    var difficultyLevel: String = String()
    var moduleStats: ArrayList<ModuleStats> = arrayListOf()

    constructor()

    constructor(day: String, month: String, year: String,difficultyLevel: String, moduleStats: ArrayList<ModuleStats>)
    {
        this.day = day
        this.month = month
        this.year = year
        this.difficultyLevel = difficultyLevel
        this.moduleStats = moduleStats
    }
}