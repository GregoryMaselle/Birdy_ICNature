package com.example.birdy

/*
    This is an interface that functions as a Data Access Object (DAO)
    for the Bird_Spottings table in BirdyDB.db. Here we define queries with
    tags so that the Room package can run those queries and return results
    in the form of a list. Each SQL operation is tagged with @Insert, @Query or
    @Delete for android Room to interpret the statement.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */

import androidx.room.*


@Dao
interface BirdSpottingsDAO {
    /*
        This SQL statement is used to add a spotting to the Bird_Spottings table.
        It takes in a BirdSpottingsEntry object and inserts it into the table.
     */
    //If there is a duplicate entry, ignore it and add this entry
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpotting(spot : BirdSpottingsEntry)
    /*
        This SQL statement is used to delete an entry from the Bird_Spottings table.
        It takes in a BirdSpottingsEntry and deletes the matching entry from the table.
        The Int that is returned is the number of entries deleted by this query.
     */
    @Delete
    fun deleteSpotting(spot : BirdSpottingsEntry) : Int
    /*
        This query is used to delete all entries from the Bird_Spottings table.
        The Int that is returned is the number of entries deleted by this query.
     */
    @Query("DELETE FROM Bird_Spottings")
    fun deleteAllSpottings() : Int
    /*
        This query is used to get the highest ID'd entry in the
        Bird_Spottings table. It is used to set the ID of new entries that are
        going to be inserted into this table. It returns the entries with the
        highest ID (which should only be 1 entry).
     */
    @Query("SELECT * FROM Bird_Spottings WHERE ID=(SELECT max(ID) FROM Bird_Spottings)")
    fun getMaxID() : List<BirdSpottingsEntry>
    /*
        This query is used to retrieve a SpottingsEntry from the Bird_Spottings
        table. This entry is then used to delete itself since the deleteSpotting
        method needs to take a BirdSpottingsEntry as an argument.
     */
    @Query("SELECT * FROM Bird_Spottings WHERE BirdName = (:name) AND SpottingDate = (:date)" +
            "AND Habitat = (:habitat)")
    fun getEntry(name : String, date: String, habitat : String) : List<BirdSpottingsEntry>
    /*
        This query is used to get a list of all spottings ordered by their ID.
        This has the affect of ordering the entries by the most recent entry.
        The most recent spottings will be at the start of the list.
     */
    @Query("SELECT * from Bird_Spottings ORDER BY ID DESC")
    fun getAllSpottings(): List<BirdSpottingsEntry>

}