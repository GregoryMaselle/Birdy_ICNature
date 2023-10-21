package com.example.birdy
/*  This class is used to display the help screen and implement the functionality of its back button.
    Taahir Suleman - SLMTAA007
    17/09/2023
 */
import android.os.Bundle
import android.widget.ImageButton

class HelpActivity: ActivityBarFunctions() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //  This method is invoked when the help screen is opened, to display the help screen, set the task bar's
        // functionality and ensure no tabs are selected on the task bar.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)  // Display the help page.
        // deselect all tabs on the task bar and set its functionality.
        super.clearTabs()
        super.setTaskbarBtns()
        backBtn()
    }

    fun backBtn(){
        // this method is used to implement the functionality of the back button to return to the previous screen from
        // which the help screen was opened.
        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener {
            // terminate the current activity (help screen) when the back button is clicked.
            finish()
        }
    }
}