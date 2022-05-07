package com.example.mathapp.authentication.classes

class Statistics {
    var summaryModules: ArrayList<ModuleStats> = arrayListOf()
    var days: ArrayList<DayStats> = arrayListOf()

    constructor()

    constructor(summaryModules: ArrayList<ModuleStats>, days: ArrayList<DayStats>)
    {
        this.summaryModules = summaryModules
        this.days = days
    }
}