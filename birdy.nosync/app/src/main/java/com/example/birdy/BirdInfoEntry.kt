package com.example.birdy
/*
    This is a data class that is used to represent an entry in the Bird_Info
    table in BirdyDB.db. Its values represent each column in the Bird_Info
    table.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */

import androidx.room.Entity
import androidx.room.PrimaryKey
/*
    @Entity tag is used to show Room that this is an entry for a table.
    This entry is for the Bird_Info table.
 */
@Entity(tableName = "Bird_Info")
data class BirdInfoEntry (
    @PrimaryKey(autoGenerate = true)
    val BirdID: Int,// This identifies the birdID as the primary key.
    val BirdName: String,
    val ScientificName: String,
    val BirdDescription: String
)