package com.example.mathapp.authentication.classes

import android.os.Parcelable
import com.example.mathapp.levels.Level
import java.io.Serializable

class User{
    var id: String = String()
    var name: String = String()
    var email: String = String()
    var modulesPassed: ArrayList<ModulesPassed> = arrayListOf()
    var userSettings: UserSettings = UserSettings()
    var userStatistics: Statistics = Statistics()
    var level: Level? = null


    constructor(id: String, name: String, email: String, modulesPassed: ArrayList<ModulesPassed>,
                userSettings: UserSettings, userStatistics: Statistics, level: Level
                )
    {
        this.id = id
        this.name = name
        this.email = email
        this.modulesPassed = modulesPassed
        this.userSettings = userSettings
        this.userStatistics = userStatistics
        this.level = level
    }
    constructor()
}