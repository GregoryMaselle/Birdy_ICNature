package com.example.birdy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
/*
    This class is used to display The Home screen. It sets up the buttons that link to various different screens.
    * Prashanth Padiachy - PDCPRA001
    * 10/08/2023
 */
class MainActivity : AppCompatActivity()  { // This class extends AppCompatActivity


//run when the class is invoked. Sets up the layout and buttons for the class.
    override fun onCreate(savedInstanceState: Bundle?) {// A function that runs when an activity is created.
        super.onCreate(savedInstanceState)
        //overridePendingTransition(R.anim.transition_left, R.anim.transition_right)
        setContentView(R.layout.activity_main)// Display the content as prescribed in the XML
        // file activity_main.xml layout
        BirdDatabase.getDatabase(this)// This will populate an in memory database that stores
        //all of the bird details.
        setHomeBtns()


    }
//This function accesses the buttons and sets up their functionality
    private fun setHomeBtns(){
        val mapbtn = findViewById<CardView>(R.id.mapCard) // Create an instance of a view Object
        // representing the CardView mapCard
        mapbtn.setOnClickListener {// A listener listening for user presses on mapbtn
            val intent = Intent(applicationContext, MapScreen::class.java)// This creates an intent which will
            intent.putExtra("Source", "Home")
            //open the MapScreen activity.
            startActivity(intent) // Start the MapScreen activity
        }

        val birdbtn = findViewById<CardView>(R.id.birdCard)// Create an instance of a view Object
        // representing the CardView birdCard
        birdbtn.setOnClickListener {// A listener listening for user presses on birdbtn
            val intent = Intent(applicationContext, SearchActivity::class.java)// This creates an intent
            // which will open the SearchActivity activity.
            startActivity(intent) // Start the searchActivity activity
            //overridePendingTransition(R.anim.transition_zoom_in, R.anim.transition_static)
        }

        val spotbtn = findViewById<CardView>(R.id.spotCard)// Create an instance of a view
        // Object representing the CardView spotCard
        spotbtn.setOnClickListener {// A listener listening for user presses on spotbtn
            var spot: BirdSpotted = BirdSpotted("", "", "")// A BirdSpotted object
            // is created with empty arguments. This is needed as the Spottings activity expects this object in the intent.
            val intent = Intent(applicationContext, Spottings::class.java)// This creates an intent which will
            //open the Spottings activity.
            intent.putExtra("SpottedBird", spot) // The BirdSpotted object is put into the intent
            startActivity(intent) // This starts the Spottings activity with the edited intent
        }

        val infobtn = findViewById<CardView>(R.id.infoCard)// Create an instance of a
        // view Object representing the CardView infoCard
        infobtn.setOnClickListener { // A listener listening for user presses on infobtn
            val intent = Intent(applicationContext, InformationActivity::class.java)// This creates an intent which will
            //open the InformationActivity activity.
            startActivity(intent) // Start the InformationActivity activity
        }

        val findbtn = findViewById<CardView>(R.id.findCard)// Create an instance of a
        // view Object representing the CardView infoCard
        findbtn.setOnClickListener { // A listener listening for user presses on infobtn
            val intent = Intent(applicationContext, FindBirdByFeatures::class.java)// This creates an intent which will
            //open the InformationActivity activity.
            startActivity(intent) // Start the InformationActivity activity
        }

        val helpbtn = findViewById<CardView>(R.id.helpCard)

        helpbtn.setOnClickListener {
            val intent = Intent(applicationContext, HelpActivity::class.java)
            startActivity(intent) // Open the Help screen
        }
    }
}