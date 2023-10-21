package com.example.birdy
/*
    This class is a parent class that other class inherit if they are used in the displaying
    of a screen that uses the activity bar. For example, SearchActivity.kt will extend this class
    because it has an action bar in its display.
    * Gregory Maselle - MSLGRE001
    * 30/08/2023
 */
import android.content.Intent
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.tabs.TabLayout

open class ActivityBarFunctions: AppCompatActivity(), PopupMenu.OnMenuItemClickListener { // This class extends AppCompatAcitivity

    init{}

    /*
        This function is called when the 'more' button in the action bar is pressed.
        Its responsible for fetching menu items for the buttons and displaying them in
        a popup menu.
     */
    fun showMenu(v: View) {
        PopupMenu(this, v).apply {
            setOnMenuItemClickListener(this@ActivityBarFunctions)
            inflate(R.menu.more_menu)
            show()
        }
    }
    /*
        This function is responsible for handling clicks on menu items displayed by
        the 'more' button in the action bar. It checks the ID of the clicked object and
        opens the required screen based on the ID.
     */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.natureReserveInfoItem){ // Nature reserve info requested
            val intent = Intent(applicationContext, InformationActivity::class.java)// Create
            // an intent to open the associated activity
            startActivity(intent)
        }
        else if (item.itemId == R.id.help){ // application help requested
            val intent = Intent(applicationContext, HelpActivity::class.java)
            startActivity(intent)
        }
        else{ // Characteristic info requested.
            val intent = Intent(applicationContext, CharactersticInfoActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    /*
        This function is used to initialize the functionality of the task bar buttons.
        Without this function being called by the subclasses, the action bar would not
        be functional.
     */
    fun setTaskbarBtns() {
        val tablayout = findViewById<TabLayout>(R.id.tabLayout)
        /*
            This addOnTabSelectedListener implements an interface that requires that
            it define onTabSelected, onTabUnselected and onTabReselected. We only use the first
            two of the three but onTabUnselected needs to be written due to the interface implemented.
         */
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            /*
                This method listens for when a tab in the activity bar is selected and it was
                not previously selected.
             */
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {// if the first tab is selected
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else if (tab.position == 1){ // if the second tab is selected
                    val intent = Intent(applicationContext, SearchActivity::class.java)
                    startActivity(intent)
                }
                else{//The third (spottings) tab is selected.

                    // A BirdSpotted object is created with empty arguments. This is needed as the spottings
                    // activity expects this object in the intent.
                    var spot: BirdSpotted = BirdSpotted("", "", "")
                    val intent = Intent(applicationContext, Spottings::class.java)
                    intent.putExtra("SpottedBird", spot)
                    startActivity(intent)
                }
            }
            //An unimplemented method required due to the interface being implemented for the
            //listener
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            /*
                This method is used mainly to enable the functionality of all tabs not appearing
                as selected. It works with clearTabs. Please refer to that method for full explanation.
             */
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position == 0){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
    /*
        This function will be called by subclasses that display a screen that are not the
        Home, Search or Spottings screens. It 'clears' the action bar. It does this by setting the
        default selected tab (Home) indicator to white, hiding the indicator and changing the
        text colour to the default unselected colour. Then, if home
        is selected while on these screens, onTabReselected is run to enable proper functionality
        of the activity bar.
     */
    fun clearTabs(){
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        tabLayout.setTabTextColors(Color.parseColor("#808080"), Color.parseColor("#808080"))
    }

}