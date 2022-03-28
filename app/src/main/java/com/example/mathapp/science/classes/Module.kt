package com.example.mathapp.science.classes

class Module {

        var id: String = "-1"
        var name: String = String()
        var images: ArrayList<PictureLocalization> = arrayListOf()
        var sections: ArrayList<Section> = arrayListOf()
        //var paragraph: ArrayList<Paragraph> = arrayListOf()

    constructor()

    constructor(id: String, name: String, images: ArrayList<PictureLocalization>, sections: ArrayList<Section>)
    {
        this.id = id
        this.name = name
        this.images = images
        this.sections = sections
    }





}