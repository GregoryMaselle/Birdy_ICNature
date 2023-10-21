package com.example.birdy
/*
    This is a data class that is used to represent an entry in the Bird_Features
    table in BirdyDB.db. Its values represent each column in the Bird_Features
    table.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
    @Entity tag is used to show Room that this is an entry for a table.
    This entry is for the Bird_Features table.
 */
@Entity(tableName = "Bird_Features")
data class BirdFeaturesEntry(
    @PrimaryKey val BirdName : String, // This identifies the birdName as the primary key.
    val BillColour : String,
    val BillShape : String,
    val Height : String,
    val UnderpartsColour :String,
    val UpperpartsColour :String
)
