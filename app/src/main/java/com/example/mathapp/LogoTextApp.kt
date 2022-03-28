package com.example.mathapp

import com.example.mathapp.science.classes.PictureLocalization
import kotlin.math.log

class LogoTextApp {
    var logo: String = String()
    var text: String = String()

    constructor()

    constructor(logo: String, text: String)
    {
        this.logo = logo
        this.text = text
    }
}