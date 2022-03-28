package com.example.mathapp.science.classes

class Paragraph {
    var position: String = "0"
    var viewType: String = String()
    var text: String? = String()
    var imageLocalization: PictureLocalization? = PictureLocalization()

    constructor()

    constructor(position: String, viewType: String, text: String)
    {
        this.position = position
        this.viewType = viewType
        this.text = text
    }
    constructor(position: String, viewType: String, imageLocalization: PictureLocalization?)
    {
        this.position = position
        this.viewType = viewType
        this.imageLocalization = imageLocalization

    }

}