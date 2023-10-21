package com.example.birdy
/*
    This is an interface that functions as a Data Access Object (DAO)
    for the Bird_Info table in BirdyDB.db. Here we define queries with
    tags so that the Room package can run those queries and return results
    in the form of a list. Each Query is tagged with @Query for Room.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */

import androidx.room.Dao
import androidx.room.Query

/*
    All of the following queries are running select statements on the
    Bird_Info table. For each query I will summarize the gist of the query.
    All the queries return a list of BirdInfoEntrys - each BirdInfoEntry
    has the exact fields that an entry in bird_features would have.
 */

@Dao
interface BirdInfoDAO {
    /*
    This query returns entries in the database that have a matching
    birdName in the BirdName field. Its used to get the description text of a
    particular bird.
     */
    @Query("SELECT * FROM Bird_Info WHERE BirdName = (:birdName)")
    fun getBirdDesc(birdName: String) : List<BirdInfoEntry>
    /*
    This query returns entries in the database that have a matching
    scientificName in the ScientificName field. Its used to get the common Name
    of a bird given its scientificname.
     */
    @Query("SELECT * FROM Bird_Info WHERE ScientificName = (:scientficName)")
    fun getBirdName(scientficName: String) : List<BirdInfoEntry>
    /*
    This query returns all the entries in the Bird_Info table in alphabetical order
    of common bird names. This is for displaying common names in alphabetical order
    in SearchActivity
     */
    @Query("SELECT * FROM Bird_Info ORDER BY BirdName")
    fun getAllBirdsCommon() : List<BirdInfoEntry>
    /*
    This query returns all the entries in the Bird_Info table in alphabetical order
    of scientific bird names. This is for displaying scientific names in alphabetical order
    in SearchActivity
     */
    @Query("SELECT * FROM Bird_Info ORDER BY ScientificName")
    fun getAllBirdsScientific() : List<BirdInfoEntry>

}