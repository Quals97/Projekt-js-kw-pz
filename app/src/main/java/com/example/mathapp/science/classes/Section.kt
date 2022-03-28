package com.example.mathapp.science.classes

class Section {
    var id: String = String()
    var name: String? = String()
    var paragraphs: ArrayList<Paragraph> = arrayListOf()

    constructor()

    constructor(id: String, name: String, paragraphs: ArrayList<Paragraph>)
    {
        this.id = id
        this.name = name
        this.paragraphs = paragraphs
    }
}