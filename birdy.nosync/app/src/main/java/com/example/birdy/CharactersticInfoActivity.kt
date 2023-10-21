package com.example.birdy
/*  This class is used to set the screen to the characteristics info screen when it is clicked on from the task bar
    or find bird by features screen.
    Gregory Maselle - MSLGRE001
    14/09/2023
 */
import android.os.Bundle
import android.widget.ImageButton

class CharactersticInfoActivity : ActivityBarFunctions() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //  This method is invoked when the characteristic info screen is opened, to display this screen, set the task
        //  bar's functionality and ensure no tabs are selected on the task bar.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characterstic_info)
        super.clearTabs()
        super.setTaskbarBtns()
        backBtn()
    }

    private fun backBtn(){
        // this method is used to implement the functionality of the back button to return to the previous screen from
        // which the characteristics info screen was opened.
        val backBtn = findViewById<ImageButton>(R.id.back_btn)

        backBtn.setOnClickListener {
            // terminate the current activity when the back button is clicked.
            finish()
        }
    }
}