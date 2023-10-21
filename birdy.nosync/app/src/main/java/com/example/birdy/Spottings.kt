package com.example.birdy
/* This class is used to display a screen with a list of the user's spottings, allowing the user to either navigate
   to a specific bird's details screen, or to delete one or all spottings.
   * Taahir Suleman - SLMTAA007
   * 03/09/2023
 */
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast

class Spottings: ActivityBarFunctions() {
    // The class inherits from ActivityBarFunctions to implement the activity bar's functionality.
    private var spottings: ArrayList<String> = arrayListOf() // An array of Spottings (Bird names as Strings)
    override fun onCreate(savedInstanceState: Bundle?) {
        //  This method is invoked when the spottings screen is opened. It receives a new bird spotting as a
        //  BirdSpotted object through the intent, which it inserts into the Bird_Spottings table if the object's
        //  name property is not an empty string.
        //  This method sets the screen to display the bird spottings screen before invoking the relevant methods.
        super.onCreate(savedInstanceState)
        val birdSpotted = (intent.getSerializableExtra("SpottedBird") as? BirdSpotted) !!
        setContentView(R.layout.activity_spottings)
        // the following two method calls are used to set the activity bar to highlight spottings as the user is on
        // the spottings screen and to set the functionality of the activity bar's buttons.
        setUpTab()
        super.setTaskbarBtns()
        deleteBtn()
        addSpotting(birdSpotted)
        spottingsList()
    }


    private fun setUpTab(){
        // This method is used to set the task bar to reflect that the user is on the spottings screen by setting
        // the spottings button on the task bar to show as selected.
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        tabLayout.getTabAt(2)?.select() // Find the tab associated with Spottings, and select it.
    }

    private fun addSpotting(bird: BirdSpotted){
        // This method inserts the newly added bird spotting to the Bird_Spottings table in the BirdDatabase if the
        // new spotting is not empty. If the spotting is empty, this means that the spotting screen was not opened
        // through adding a new spotting, hence there is no new spotting to insert.
        val db = BirdDatabase.getDatabase(this)
        if(bird.getName() != "") {
            // A new spotting is only inserted if the name in the provided BirdSpotted object is not empty.
            val maxIDCheck = db.birdSpottingsDao().getMaxID()
            if (maxIDCheck.isEmpty()){
                // If the Bird_Spottings table is currently empty, the new spotting is added with an ID of 1, and the
                // other properties given by the provided BirdSpotted object.
                db.birdSpottingsDao().insertSpotting(BirdSpottingsEntry(1,bird.getName(),bird.getDate(),bird.getHabitat()))
            }
            else{
                // If there are other entries currently present in the Bird_Spottings table, the new spotting is
                // inserted into the table with the next available ID and the properties given by the provided
                // BirdSpotted object as an argument.
                val maxID = maxIDCheck[0].ID
                db.birdSpottingsDao().insertSpotting(BirdSpottingsEntry(maxID+1,bird.getName(),bird.getDate(),bird.getHabitat()))
            }
        }
    }

    private fun getSpottings(){
        // This method retrieves all the spottings currently in the Bird_Spottings table, and populates the spottings
        // array list accordingly.
        val db = BirdDatabase.getDatabase(this)
        val dbSpottings = db.birdSpottingsDao().getAllSpottings()
        for (spottingEntry in dbSpottings){
            spottings.add(spottingEntry.BirdName +"\n"+spottingEntry.SpottingDate +"\n"+spottingEntry.Habitat)
        }
    }

    private fun spottingsList(){
        // This method first populates the spottings array list and then displays it on the screen's list view.
        // Additionally, this method implements the functionality of navigating the user to the appropriate bird info
        // screen when a specific bird spotting is clicked, using the common name of the bird spotted.
        getSpottings()
        val birdList = findViewById<ListView>(R.id.name_list)// Create instance of a view for name_list

        // Create an array adapter to transform the spottings array list to be displayed on the list view, and assign
        // this array adapter to the list view.
        val birdsAdapter = ArrayAdapter<String>(this, R.layout.activity_spottings_row, spottings)
        birdList.adapter = birdsAdapter

        birdList.setOnItemClickListener { parent, view, position, id ->
            // This listens for clicks on the birdList ListView
            // When a spotting is clicked on the list view, the bird information display screen of the associated bird
            // is opened.
            for(i in 0..spottings.size){
                if (position == i) {
                    val value = birdList.adapter.getItem(position).toString().split("\n")[0]
                    val intent = Intent(applicationContext, BirdInformationDisplay::class.java)
                    intent.putExtra("Name", value)
                    startActivity(intent)
                }
            }
        }
    }

    private fun deleteBtn(){
        // This method implements the functionality to open the delete spottings screen when the user clicks on the
        // delete button, only if there is at least one bird spotting to delete.
        val deleteBtn = findViewById<ImageButton>(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            // if there are no spottings to delete when the user clicks the delete button, then the user is notified of
            // this and remains on the spottings screen. Otherwise, the delete spotting screen is opened and the
            // spottings list is sent through the intent.
            if(spottings.isEmpty())
                Toast.makeText(this, "NO SPOTTINGS TO DELETE", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(applicationContext, DeleteSpotting::class.java)
                intent.putStringArrayListExtra("Spottings", spottings)
                startActivity(intent)
            }
        }
    }


}