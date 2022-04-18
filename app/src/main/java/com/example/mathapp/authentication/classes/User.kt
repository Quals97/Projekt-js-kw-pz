package com.example.mathapp.authentication.classes

class User {
    var id: String = String()
    var name: String = String()
    var email: String = String()
    var modulesPassed: ArrayList<ModulesPassed> = arrayListOf()
    var userSettings: UserSettings = UserSettings()


    constructor(id: String, name: String, email: String, modulesPassed: ArrayList<ModulesPassed>)
    {
        this.id = id
        this.name = name
        this.email = email
        this.modulesPassed = modulesPassed
    }
    constructor()
}