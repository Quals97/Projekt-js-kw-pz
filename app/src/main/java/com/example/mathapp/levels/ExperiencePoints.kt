package com.example.mathapp.levels

object ExperiencePerLevel {
    var experiencePerLevel: List<Int> = arrayListOf(
        0,
        10,
        20,
        40,
        80,
        160,
        320,
        640,
        1280,
        2560,
        5120,
        10240,
        20480




    )
    fun calculateLevel(level: Level, scoringSystem: Int): Level {

        var levelu = level.level.toInt()
        var points = level.points.toInt()

        if (level.points.toInt() >= experiencePerLevel[level.level.toInt()]
            && points + scoringSystem < experiencePerLevel[level.level.toInt() + 1]
        ) {
            points += scoringSystem

        }else{
            levelu += 1
            points += scoringSystem

        }

        return Level(levelu.toString(), points.toString())
    }



}