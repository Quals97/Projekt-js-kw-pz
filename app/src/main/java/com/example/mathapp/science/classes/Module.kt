package com.example.mathapp.science.classes

class Module {

        var id: String = "0"
        var name: String = String()
        var images: ArrayList<String> = arrayListOf()
        var paragraph: ArrayList<Paragraph> = arrayListOf()

    constructor(id: String, name: String, images: ArrayList<String>, paragraph: ArrayList<Paragraph>)
    {
        this.id = id
        this.name = name
        this.images = images
        this.paragraph = paragraph
    }

    constructor()



}