package com.example.birdy

import android.os.Bundle
import android.widget.ImageButton
/*
    This class is used to display a map of the reserve. The map can be zoomed in and out of.
    * Prashanth Padiachy - PDCPRA001
    * 27/08/2023
 */
class MapScreen : ActivityBarFunctions() { //This class extends AppCompatActivity class
    /*
This method is run when the class is invoked. It sets the layout of the class, sets up the taskbar, sets up the backButton and sets the map to have a
grid if the map is accessed from the addSpotting screen
*/
    override fun onCreate(savedInstanceState: Bundle?) { // A function that runs when
        // an activity is created.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_screen)// Display the content as prescribed in the XML
        // file activity_map_screen.xml layout

        checkGrid()
        setTaskbarBtns()
        clearTabs()
        backBtn()
    }
    /*
This method checks if the map is accessed from the addSpotting screen. If so, the map with the grid indicating the
different habitats is shown.
*/
    private fun checkGrid(){
        val sourceScreen = intent.getStringExtra("Source")
        val mapImage = findViewById<com.ortiz.touchview.TouchImageView>(R.id.mapPicture)
        if(sourceScreen == "Spottings"){
            val mapID = resources.getIdentifier("com.example.birdy:drawable/mapwithgrid", null, null)
            mapImage.setImageResource(mapID)
        }}
    /*
    This method is used to enable the functionality of a back button in the
    activity.
 */
    private fun backBtn(){
        val backBtn = findViewById<ImageButton>(R.id.back_btn)

        backBtn.setOnClickListener {
            // terminate the current activity when the back button is pressed.
            finish()
        }
    }
}