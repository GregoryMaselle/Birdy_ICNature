package com.example.birdy
/*  This class represents a bird spotting and is used when adding a spotting to send the details of the spotting
    to the spottings screen to be inserted into the database when a spotting is added.
    Taahir Suleman - SLMTAA007
    28/08/2023
 */
class BirdSpotted: Bird{

    private lateinit var date:String // The date of the spotting
    private lateinit var habitat:String // The habitat of the spotting

    //A constructor that sets the date and habitat of the BirdSpotted object.
    //super() is used to call the constructor of the parent class, Bird, to set the name of the BirdSpotted
    constructor(name:String, date:String, habitat: String) : super(name) {
        this.date = date
        this.habitat = habitat
    }

    public fun getDate():String{ // A get function for the Date of the BirdSpotted
        return date
    }

    public fun getHabitat():String{ // A get function for the habitat of the BirdSpotted
        return habitat
    }

    public fun setDate(d: String){ // A set function for the Date of the BirdSpotted
        date = d
    }

    public fun setHabitat(h: String){ // A set function for the habitat of the BirdSpotted
        habitat = h
    }

}