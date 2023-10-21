package com.example.birdy
/*
    This class functions as an abstraction of the database the application
    uses to store bird info, bird features and user spottings. It uses tags
    and implements RoomDatabase() to tell Rooom (the DB management package) that
    this is the database abstraction. This class is a singleton, so only one
    instance of it can exist at a time. This class also creates the database
    in the local storage of the device once and not again.
    *Gregory Maselle - MSLGRE001
    *07/09/2023
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    The arguments in @Database are interfaces that we identify as representing the
    entries in tables in the database for each table. Version number represents the version
    of the database we are using, which is the first ever version. This will have
    to change if a new database is uploaded in the next app update.
 */
@Database(entities = [BirdInfoEntry::class, BirdFeaturesEntry::class, BirdSpottingsEntry::class], version = 1)
abstract class BirdDatabase : RoomDatabase() {
    /*
        Lines 29-31 are just instantiating the DAO interfaces for each table
        so they are accessible via an instance of this class.
     */
    abstract fun birdInfoDao(): BirdInfoDAO
    abstract fun birdFeaturesDao(): BirdFeaturesDAO
    abstract fun birdSpottingsDao(): BirdSpottingsDAO
    // This companion object is how we implement the singleton.
    // Only one instance of this companion object can exist.
    companion object {
        private var INSTANCE: BirdDatabase? = null
        /*
            getDatabase is what a class calls to get an instance of the database.
            If an instance exists already, return the instance.
            If it doesnt exist, create an instance and return it.
         */
        fun getDatabase(context: Context): BirdDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        /*
                        Lines 50-53 are one method call to create the database
                        from the files provided in the assets folder that comes
                        with the installation of the application.
                         */
                        Room.databaseBuilder(context, BirdDatabase::class.java, "bird_database")
                            .createFromAsset("BirdyDB.db")
                            .allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }
    }
}