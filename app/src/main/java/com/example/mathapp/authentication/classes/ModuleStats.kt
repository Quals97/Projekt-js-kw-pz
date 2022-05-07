package com.example.mathapp.authentication.classes

class ModuleStats {
    var id: String = String()
    var moduleName: String = String()
    var points: String = String()
    var numberOfGames: String = String()
    var difficultyLevel: String = String()

    constructor()

    constructor(id: String, moduleName: String, points: String, numberOfPoints: String,
                difficultyLevel: String)
    {
        this.id = id
        this.moduleName = moduleName
        this.points = points
        this.numberOfGames = numberOfPoints
        this.difficultyLevel = difficultyLevel
    }
}