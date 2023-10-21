package com.example.birdy
/*
    This is an interface that functions as a Data Access Object (DAO)
    for the Bird_Features table in BirdyDB.db. Here we define queries with
    tags so that the Room package can run those queries and return results
    in the form of a list. Each Query is tagged with @Query for Room.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */
import androidx.room.Dao
import androidx.room.Query

/*
    All of the following queries are running select statements on the
    Bird_features table. For each query I will summarize the gist of the query.
    All the queries return a list of BirdFeaturesEntrys - each BirdFeatureEntry
    has the exact fields that an entry in bird_features would have.

 */

@Dao
interface BirdFeaturesDAO {
    /*
    This query gets entries that match the entered features exactly.
     */
    @Query("SELECT * FROM Bird_Features WHERE BillShape = (:bs) AND Height=(:h) AND " +
            "UpperpartsColour LIKE '%' || (:upperC) || '%' AND UnderpartsColour LIKE '%' || (:underC) || '%'AND " +
            "BillColour LIKE '%' || (:bc) || '%'")
    fun getFullMatches(bc: String, bs:String,h:String, underC:String, upperC:String ) : List<BirdFeaturesEntry>
    /*
    This query gets entries that match the entered features 80%. The height
    does not have to match.
     */
    @Query("SELECT * FROM Bird_Features WHERE BillShape = (:bs) AND " +
            "UpperpartsColour LIKE '%' || (:upperC) ||'%' AND UnderpartsColour LIKE '%' || (:underC) || '%' AND " +
            "BillColour LIKE '%' || (:bc) || '%'")
    fun getNoHeight(bc: String, bs:String, underC:String, upperC:String ) : List<BirdFeaturesEntry>
    /*
    This query gets entries that match the entered features 80%. The upperparts colour
    does not have to match.
     */
    @Query("SELECT * FROM Bird_Features WHERE BillShape = (:bs) AND " +
            "Height = (:h) AND UnderpartsColour LIKE '%' || (:underC) || '%' AND " +
            "BillColour LIKE '%' || (:bc) || '%'")
    fun getNoUpper(bc: String, bs:String,h:String, underC:String) : List<BirdFeaturesEntry>
    /*
    This query gets entries that match the entered features 80%. The underparts colour
    does not have to match.
     */
    @Query("SELECT * FROM Bird_Features WHERE BillShape = (:bs) AND " +
            "Height = (:h) AND UpperpartsColour LIKE '%' || (:upperC) || '%' AND " +
            "BillColour LIKE '%' || (:bc) || '%' ")
    fun getNoUnder(bc: String, bs:String,h:String, upperC:String) : List<BirdFeaturesEntry>
    /*
    This query gets entries that match the entered features 80%. The bill shape
    does not have to match.
     */
    @Query("SELECT * FROM Bird_Features WHERE UpperpartsColour LIKE '%' || (:upperC) || '%' AND " +
            "Height = (:h) AND UnderpartsColour LIKE '%' || (:underC) || '%' AND " +
            "BillColour LIKE '%' || (:bc) || '%'")
    fun getNoShape(bc: String,h:String,underC:String,upperC:String) : List<BirdFeaturesEntry>
    /*
    This query gets entries that match the entered features 80%. The bill colour
    does not have to match.
     */
    @Query("SELECT * FROM Bird_Features WHERE UpperpartsColour LIKE '%' || (:upperC) || '%' AND " +
            "Height = (:h) AND UnderpartsColour LIKE '%' || (:underC) || '%' AND " +
            "BillShape = (:bs)")
    fun getNoBillColour(bs: String,h:String,underC:String,upperC:String) : List<BirdFeaturesEntry>
    /*
    This query returns the features of a specific bird.
     */
    @Query("SELECT * FROM Bird_Features WHERE BirdName = (:birdName)")
    fun getBirdFeatures(birdName:String) : List<BirdFeaturesEntry>
}