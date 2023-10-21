package com.example.birdy
/*  This class can be used to create Bird objects with a bird's name and defining features.
    Taahir Suleman - SLMTAA007
    28/08/2023
 */
import java.io.Serializable
// The class extends the Serializable class so as to facilitate putting
// an object of this class into an intent via putSerializableExtra().
open class Bird(): Serializable {
    private lateinit var name:String
    private lateinit var billColour:String
    private lateinit var billShape:String
    private lateinit var height:String
    private lateinit var underpartsColour:String
    private lateinit var upperpartsColour:String

    constructor(n: String) : this() { // A constructor used to set the name of the bird object.
        name = n
    }

    constructor(n: String, bColour: String, bShape: String, h: String, underparts: String, upperparts: String): this(n){
        // constructor used to set a bird's name and features.
        // this constructor is not currently used but may be useful if new birds should be inserted into the Bird_Features
        // table in the database.
        // this is also true for all of the properties except for the bird name, which is used for adding bird spottings.
        billColour = bColour
        billShape = bShape
        height = h
        underpartsColour = underparts
        upperpartsColour = upperparts
    }

    public fun getName(): String { // A get method to access the bird's name.
        return name
    }

    public fun setName(n: String){ // A set method to set the bird's name.
        name = n
    }

    public fun getBillColour(): String{ // A get method to access the bird's bill colour.
        return billColour
    }

    public fun getBillShape(): String{ // A get method to access the bird's bill shape.
        return billShape
    }

    public fun getHeight(): String{ // A get method to access the bird's height.
        return height
    }

    public fun getUnderpartsColour(): String{ // A get method to access the bird's underparts colour.
        return underpartsColour
    }

    public fun getUpperpartsColour(): String{ // A get method to access the bird's upperparts colour.
        return upperpartsColour
    }

    public fun setBillColour(bColur: String){ // A set method to set the bird's bill colour.
        billColour = bColur
    }

    public fun setBillShape(bShape: String){ // A set method to set the bird's shape.
        billShape = bShape
    }

    public fun setHeight(h: String){ // A set method to set the bird's height.
        height = h
    }

    public fun setUnderpartsColour(underparts: String){ // A set method to set the bird's underparts colour.
        underpartsColour = underparts
    }

    public fun setUpperpartsColour(upperparts: String){ // A set method to set the bird's upperparts colour.
        upperpartsColour = upperparts
    }

    @Override
    public fun equals(b: Bird): Boolean{
        return (name == b.getName()) && (billColour == b.getBillColour()) && (billShape == b.getBillShape()) && (height == b.getHeight()) && (underpartsColour == b.getUnderpartsColour()) && (upperpartsColour == b.getUpperpartsColour())
    }
}