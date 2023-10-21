package com.example.birdy

import org.junit.jupiter.api.Assertions.*
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class BirdDatabaseTest: TestCase(){
    private lateinit var db: BirdDatabase
    private lateinit var spottingsDao: BirdSpottingsDAO
    private lateinit var infoDao: BirdInfoDAO
    private lateinit var featuresDao: BirdFeaturesDAO


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variables
        db = BirdDatabase.getDatabase(context)
        spottingsDao = db.birdSpottingsDao()
        infoDao = db.birdInfoDao()
        featuresDao = db.birdFeaturesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun addAndRetrieveSpottings() = runBlocking {
        val spotting = BirdSpottingsEntry(1, "African Snipe", "20/09/2023", "C2")
        spottingsDao.insertSpotting(spotting)
        val languages = spottingsDao.getAllSpottings()
        assertThat(languages.contains(spotting)).isTrue()
    }

    @Test
    fun deleteAllSpottings(){
        val confirmed = spottingsDao.deleteAllSpottings()
        assertThat(confirmed != -1).isTrue()
    }

    @Test
    fun readBirdName1(){
        val bird = infoDao.getBirdName("Passer melanurus")
        assertThat(bird[0].BirdName == "Cape Sparrow").isTrue()
    }

    @Test
    fun readBirdName2(){
        val bird = infoDao.getBirdName("Nectarinia famosa")
        assertThat(bird[0].BirdName == "Malachite Sunbird").isTrue()
    }

    @Test
    fun readBirdName3(){
        val bird = infoDao.getBirdName("Thalasseus bergii")
        assertThat(bird[0].BirdName == "Swift Tern").isTrue()
    }

    @Test
    fun readBirdName4(){
        val bird = infoDao.getBirdName("Crithagra sulphurata")
        assertThat(bird[0].BirdName == "Bully Canary").isTrue()
    }

    @Test
    fun readBirdName5(){
        val bird = infoDao.getBirdName("Estrilda astrild")
        assertThat(bird[0].BirdName == "Common Waxbill").isTrue()
    }

    @Test
    fun readBirdFeatures(){
        val birdFeatures = featuresDao.getBirdFeatures("Alpine Swift")
        assertThat(birdFeatures[0].BillColour == "Black").isTrue()
    }


}