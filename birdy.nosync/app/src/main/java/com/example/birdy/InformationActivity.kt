package com.example.birdy

import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
/*
    This class is used to display information about the nature reserve.
    * Prashanth Padiachy - PDCPRA001
    * 10/09/2023
 */
class InformationActivity : ActivityBarFunctions() { // The class inherits ActivityBarFunctions for Action Bar functionality
    /*
  This function is run when the informationActivity is invoked. It sets the layout of the class to the reserveInformation xml file and sets up the taskbar and back buttons
 */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)// Display the content as prescribed in the XML file activity_info.xml layout
        clearTabs()
        super.setTaskbarBtns() // This method is used to enable to functionality of the task bar buttons.
        backBtn()
    }

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