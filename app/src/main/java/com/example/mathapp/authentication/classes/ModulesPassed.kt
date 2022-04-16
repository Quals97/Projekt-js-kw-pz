package com.example.mathapp.authentication.classes

class ModulesPassed {
    var id:String = String()
    var name: String = String()
    var status: String = "false"
    var points: String = String()

    constructor(id: String, name: String, status: String, points: String)
    {
        this.id = id
        this.name = name
        this.status = status
        this.points = points
    }
    constructor()

}