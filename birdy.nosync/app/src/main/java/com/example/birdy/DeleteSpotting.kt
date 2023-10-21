package com.example.birdy
/*  This class implements the screen used by users to delete bird spottings. It develops several alerts used to
    prompt users to select a spotting to delete, confirm deletions and return to the spottings page. It also develops
    a button which allows users to delete all of their bird spottings.
    Author: Taahir Suleman - SLMTAA007
    Date: 05/09/2023
 */

import android.app.AlertDialog
import android.os.Bundle
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast

class DeleteSpotting: ActivityBarFunctions() {
    // The class inherits from ActivityBarFunctions to implement the activity bar's functionality.
    private lateinit var spottings: ArrayList<String>
    private lateinit var spottingsList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        // This method is invoked when the delete spotting screen is opened, to set the current screen to the delete
        // spottings screen and invoke the appropriate methods which implement this screen's functionality.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_spotting)// Display the content as prescribed in the XML
        // the clearTabs method is used to ensure that no tab on the activity bar is selected when the user is on
        // the delete spotting screen, since this is not one of the screens in the activity bar.
        super.clearTabs()
        super.setTaskbarBtns() // sets the functionality of the task bar's buttons.
        initialAlert()
        // receives the array list of spottings through the intent, displays it and implements the functionality of
        // clicking on a spotting to delete it.
        spottings = intent.getStringArrayListExtra("Spottings")!!
        spottingsList = findViewById(R.id.spottings_list)
        spottingsList(spottings)
        // implements the delete all button's functionality
        val deleteAllBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.deleteAllBtn)
        deleteAllBtn.setOnClickListener {
            deleteAll()
        }
    }

    private fun initialAlert(){
        // This method develops the alert that pops up when the delete spotting screen is opened, prompting the user
        // to select which spotting they would like to delete.
        val alertBuilder = AlertDialog.Builder(this).create()
        alertBuilder.setTitle("Delete spotting")
        alertBuilder.setMessage("Please Select a spotting to delete")
        // The alert is built step by step, with the title at the top and message in the alert first set.
        // A single button labeled ok is implemented on the alert to be used by the user to dismiss the alert.
        alertBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { dialog, which -> dialog.dismiss() }
        alertBuilder.show() // once the alert is fully developed, it is displayed to the user.
        // the following four lines are used to set the position of the "Ok" button in the alert to be in the middle
        // at the bottom of the alert.
        val btnPositive = alertBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 20f
        btnPositive.layoutParams = layoutParams
    }

    private fun spottingsList(spottings: ArrayList<String>){
        val spottingsAdapter = ArrayAdapter(this, R.layout.activity_delete_spottings_row, spottings)
        spottingsList.adapter = spottingsAdapter
        spottingsList.setOnItemClickListener { parent, view, position, id ->
            for (i in 0..spottings.size) {
                if (position == i) {
                    //var value = spottingsList.adapter.getItem(position).toString()
                    //value = value.subSequence(0, value.indexOf("\n")).toString()
                    // delete from database here
                    deleteConfirmation(position)
                    //returnToSpottings()
                    break
                }
            }
        }
    }

    private fun deleteConfirmation(position: Int){
        /*  This method develops the alert that pops up when a user selects a spotting to delete, prompting the user
            to confirm their deletion. If the user confirms the deletion, the spotting is deleted from the
            Bird_Spottings table in the database and removed from the array list being displayed on the screen.
        */
        val builder = AlertDialog.Builder(this).create()
        builder.setTitle("Deletion confirmation")
        builder.setMessage("Are you sure you want to delete this spotting?")
        // The alert is built step by step, with the title at the top and message in the alert first set.
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, which ->
            // If the user confirms the deletion, the spotting is deleted from the database and array list.
            val db = BirdDatabase.getDatabase(this)
            val birdInfoArray = spottings[position].split("\n")
            // Retrieve the details of the spotting clicked on in order to use it to delete the spotting from the
            // database using the deleteSpotting() query.
            val birdName = birdInfoArray[0]
            val date = birdInfoArray[1]
            val habitat = birdInfoArray[2]
            val id = db.birdSpottingsDao().getEntry(birdName,date,habitat)[0].ID
            // Delete the spotting that was clicked on from the database.
            val confirm : Int = db.birdSpottingsDao().deleteSpotting(BirdSpottingsEntry(id,birdName,date,habitat))

            if(confirm != -1){
                // If the deletion is successful, the spotting is removed from the array list and the list view is updated
                // accordingly, and the user is prompted to return to the spottings screen.
                spottings.removeAt(position)
                val spottingsAdapter =
                    ArrayAdapter(this, R.layout.activity_delete_spottings_row, spottings)
                spottingsList.adapter = spottingsAdapter
                Toast.makeText(applicationContext,
                    "Spotting deleted", Toast.LENGTH_SHORT).show()
                returnToSpottings()
            }
            else{
                // If the deletion is unsuccessful, the user is notified and prompted to return to the spottings screen.
                Toast.makeText(applicationContext,
                    "FAILED TO DELETE SPOTTING, TRY AGAIN", Toast.LENGTH_SHORT).show()
                returnToSpottings()
            }


        }
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, which ->
            // If the user chooses not to delete the spotting, no deletion is performed.
            Toast.makeText(applicationContext,
                "Spotting not deleted", Toast.LENGTH_SHORT).show()
        }
        builder.show() // once the alert is fully developed, it is displayed to the user.
    }

    private fun returnToSpottings(){
        /*  This method develops the alert that is displayed after a user confirms a deletion to ask the user whether
            they would like to return to the spottings screen.
         */
        val builder = AlertDialog.Builder(this).create()
        builder.setTitle("Return to spottings")
        builder.setMessage("Would you like to return to the spottings screen?")
        // The alert is built step by step, with the title at the top and message in the alert first set.
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, which ->
            // If the user would like to return to the spottings screen, then the spottings screen is opened accordingly.
            val spot = BirdSpotted("", "", "")
            // A BirdSpotted object is created with empty arguments. This is needed as the Spottings activity expects
            // this object in the intent.
            val intent = Intent(applicationContext, Spottings::class.java)
            intent.putExtra("SpottedBird", spot)
            startActivity(intent)
        }
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, which ->
            // If the user chooses not to return to the spottings screen, they are prompted to select another spotting
            // to delete.
            Toast.makeText(applicationContext,
                "Please select a spotting to delete", Toast.LENGTH_SHORT).show()
        }
        builder.show() // once the alert is fully developed, it is displayed to the user.
    }

    private fun deleteAll(){
        /*  This method develops the alert that pops up when a user chooses to delete all spottings, prompting the user
            to confirm their deletion. If the user confirms the deletion, all spottings are deleted from the database and
            the user is returned to the spottings screen.
        */
        val builder = AlertDialog.Builder(this).create()
        builder.setTitle("WARNING!")
        builder.setMessage("YOU ARE ABOUT TO DELETE ALL SPOTTINGS! PLEASE CONFIRM OR CANCEL")
        // The alert is built step by step, with the title at the top and message in the alert first set.
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm") { dialog, which ->
            // if the user confirms the deletion, all spottings are deleted from the database and the bird spottings
            // screen is opened.
            val db = BirdDatabase.getDatabase(this)
            db.birdSpottingsDao().deleteAllSpottings()
            Toast.makeText(applicationContext,
                "ALL SPOTTINGS DELETED", Toast.LENGTH_SHORT).show()
            val spot = BirdSpotted("", "", "")
            // A BirdSpotted object is created with empty arguments. This is needed as the Spottings activity expects
            // this object in the intent.
            val intent = Intent(applicationContext, Spottings::class.java)//
            // This creates an intent which will open the Spottings activity.
            intent.putExtra("SpottedBird", spot) // The BirdSpotted object is put into the intent
            startActivity(intent) // This starts the Spottings activity with the edited intent
        }
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
            // if the user chooses not to confirm the deletion, no spottings are deleted.
            Toast.makeText(applicationContext,
                "DELETION CANCELLED", Toast.LENGTH_SHORT).show()
        }
        builder.show() // once the alert is fully developed, it is displayed to the user.
    }


}