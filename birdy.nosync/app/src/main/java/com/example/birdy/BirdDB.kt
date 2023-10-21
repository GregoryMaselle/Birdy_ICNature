package com.example.birdy
/*  This class is used for inserting entries into the database with the DbActivity screen.
    Taahir Suleman - SLMTAA007
    28/08/2023
 */
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/*  THIS CLASS WAS USED IN POPULATING THE BIRD_INFO AND BIRD_FEATURES TABLES IN THE DATABASE WITH THE AID OF THE DBActivity
    CLASS AND SCREEN, MODIFIED ACCORDINGLY FOR INSERTING INTO EACH RESPECTIVE TABLE.
    THIS CLASS IS NO LONGER IN USE. IT IS MERELY HERE TO DEMONSTRATE WHAT WE USED TO POPULATE THE BIRD_INFO AND
    BIRD_FEATURES TABLES IN OUR DATABASE, BEFORE SWITCHING OUR DATABASE IMPLEMENTATION TO A ROOM DATABASE.
    THE CURRENT IMPLEMENTATION WHICH IS IN USE IS GIVEN BY THE BirdDatabase CLASS, WITH THE ASSOCIATED DAOs AND
    ENTRY CLASSES.
 */

class BirdDB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION)  {

    override fun onCreate(db: SQLiteDatabase) {
        // when an instance of this class is created, the Bird_Info and Bird_Features tables are created with their
        // associated properties.
        val createBirdDB: String = ("CREATE TABLE " + BIRD_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT," +
                SCNAME_COL + " TEXT," +
                DESCRIPTION_COL + " TEXT)")

        val createFeaturesDB: String = ("CREATE TABLE " + FEATURES_TABLE + " ("
                + NAME_COL + " TEXT PRIMARY KEY," +
                BILL_COLOUR_COL + " TEXT," +
                BILL_SHAPE_COL + " TEXT," +
                HEIGHT_COL + " TEXT," +
                UNDERPARTS_COL + " TEXT," +
                UPPERPARTS_COL + " TEXT)")


        db.execSQL(createBirdDB)
        db.execSQL(createFeaturesDB)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method checks if the table already exists and does not create a new table if so.
        db.execSQL("DROP TABLE IF EXISTS $BIRD_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $FEATURES_TABLE")
        onCreate(db)
    }


    fun addEntry(name : String, scName : String, desc: String){
        // This method is for inserting data into the Bird_Info table.
        val values = ContentValues()

        // assign the given values to the columns in the Bird_Info table to be inserted.
        values.put(NAME_COL, name)
        values.put(SCNAME_COL, scName)
        values.put(DESCRIPTION_COL, desc)
        // writable instance of the database is used to insert into the database.
        val db = this.writableDatabase
        // all the above values are inserted into the Bird_Info table or an exception is thrown if any error occurs.
        db.insertOrThrow(BIRD_TABLE, null, values)
        db.close()
    }


    fun addFeatures(name: String, bColour: String, bShape: String, height: String, underparts: String, upperparts: String){
        // This method is for inserting data into the Bird_Features table.
        val insertValues = ContentValues()
        val db = this.writableDatabase

        // assign the given values to the columns in the Bird_Features table to be inserted.
        insertValues.put(NAME_COL, name)
        insertValues.put(BILL_COLOUR_COL, bColour)
        insertValues.put(BILL_SHAPE_COL, bShape)
        insertValues.put(HEIGHT_COL, height)
        insertValues.put(UNDERPARTS_COL, underparts)
        insertValues.put(UPPERPARTS_COL, upperparts)
        // all the above values are inserted into the Bird_Info table or an exception is thrown if any error occurs.
        db.insertOrThrow(FEATURES_TABLE, null, insertValues)
        db.close()
    }

    companion object{
        // variables defined for the database
        private const val DATABASE_NAME = "BirdyDB"

        private const val DATABASE_VERSION = 1

        // table names
        const val BIRD_TABLE = "Bird_Info"
        const val FEATURES_TABLE = "Bird_Features"

        // Bird_Info table columns
        const val ID_COL = "BirdID"

        const val NAME_COL = "BirdName"

        const val SCNAME_COL = "ScientificName"

        const val DESCRIPTION_COL = "BirdDescription"

        // Bird_Features table columns

        const val BILL_COLOUR_COL = "BillColour"

        const val BILL_SHAPE_COL = "BillShape"

        const val HEIGHT_COL = "Height"

        const val UNDERPARTS_COL = "UnderpartsColour"

        const val UPPERPARTS_COL = "UpperpartsColour"
    }

}