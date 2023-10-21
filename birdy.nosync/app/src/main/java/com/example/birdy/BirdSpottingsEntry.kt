package com.example.birdy
/*
    This is a data class that is used to represent an entry in the Bird_Spottings
    table in BirdyDB.db. Its values represent each column in the Bird_Spottings
    table.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
    @Entity tag is used to show Room that this is an entry for a table.
    This entry is for the Bird_Spottings table.
 */
@Entity(tableName = "Bird_Spottings")
data class BirdSpottingsEntry(
    @PrimaryKey(autoGenerate = true) val ID : Int ,// This identifies the birdID as the primary key.
    val BirdName : String,
    val SpottingDate : String,
    val Habitat : String
)
