package com.example.birdy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
/*
    This class takes user input about the features of a particular bird.
    It uses this to match the input with possible birds in the database.
    When the user clicks submit, they are taken to a relevent result screen
    * Prashanth Padiachy - PDCPRA001
    * 09/09/2023
 */
class FindBirdByFeatures : ActivityBarFunctions(){

//run when class is created. Sets layout and initialises the class' ui
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_bird_by_features)
        clearTabs()
        super.setTaskbarBtns()
        initialiseUI()

    }
//function sets up functionality of spinners and the submit button
    private fun initialiseUI(){
        //Sets up the spinner to select bird bill colour
        val billCSpinner = findViewById<Spinner>(R.id.s1)
        val billCAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.billColours,android.R.layout.simple_spinner_item)
        billCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        billCSpinner.adapter = billCAdapter

        //Sets up the spinner to select bird bill shape
        val billSSpinner = findViewById<Spinner>(R.id.s2)
        val billSAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.billShapes,android.R.layout.simple_spinner_item)
        billSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        billSSpinner.adapter = billSAdapter

        //Sets up the spinner to select bird height
        val heightSpinner = findViewById<Spinner>(R.id.s3)
        val heightAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.sizes,android.R.layout.simple_spinner_item)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        heightSpinner.adapter = heightAdapter

        //Sets up the spinner to select bird underparts colours
        val underSpinner = findViewById<Spinner>(R.id.s4)
        val underAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.coatColours,android.R.layout.simple_spinner_item)
        underAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        underSpinner.adapter = underAdapter

        //Sets up the spinner to select bird upperparts colours
        val upperSpinner = findViewById<Spinner>(R.id.s5)
        val upperAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.coatColours,android.R.layout.simple_spinner_item)
        upperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        upperSpinner.adapter = upperAdapter

        val helpButton = findViewById<ImageButton>(R.id.charHelpbtn)

        helpButton.setOnClickListener{
            // enables the help button's functionality to open the characteristics info screen.
            val intent = Intent(applicationContext, CharactersticInfoActivity::class.java)
            startActivity(intent)

        }

        val btn = findViewById<Button>(R.id.button_submit)
        btn.setOnClickListener{
            // retrieve the user input from the appropriate spinners.
            val selectedBillC = billCSpinner.selectedItem.toString()
            val selectedBillS = billSSpinner.selectedItem.toString()
            val selectedHeight = heightSpinner.selectedItem.toString()
            val selectedUnderC = underSpinner.selectedItem.toString()
            val selectedUpperC = upperSpinner.selectedItem.toString()

            /*  This block of code retrieves all the birds that match the inputted features from the Bird_Features
                table in the database. It first retrieves those birds that match all five features, and then retrieves
                the birds that match four of the given features. Duplicates are then removed from this result list.
             */
            val db = BirdDatabase.getDatabase(this)
            var matchList : List<BirdFeaturesEntry> = arrayListOf()
            matchList = matchList + db.birdFeaturesDao().getFullMatches(selectedBillC,selectedBillS,selectedHeight,selectedUnderC,selectedUpperC) +
                    db.birdFeaturesDao().getNoHeight(selectedBillC,selectedBillS,selectedUnderC,selectedUpperC) +
                    db.birdFeaturesDao().getNoUpper(selectedBillC,selectedBillS,selectedHeight,selectedUnderC) +
                    db.birdFeaturesDao().getNoUnder(selectedBillC,selectedBillS,selectedHeight,selectedUpperC) +
                    db.birdFeaturesDao().getNoShape(selectedBillC,selectedHeight,selectedUnderC,selectedUpperC) +
                    db.birdFeaturesDao().getNoBillColour(selectedBillS,selectedHeight,selectedUnderC,selectedUpperC)
            matchList = matchList.distinct()
            // Add all of the results to the array list to be displayed on the results screen.
            var line : ArrayList<String> =  arrayListOf()
            for (item in matchList){
                line.add(item.BirdName)
            }
            if(line.size == 0){
                // if no birds match the given features, notify the user of this.
                noMatchAlert()
            }
            else {
                // open the results screen and send the associated result list through the intent.
                val intent = Intent(applicationContext, BirdCharacteristicSearchResults::class.java)
                intent.putExtra("resultList", line)
                startActivity(intent)
            }
        }
    }
    //This method is triggered when there are no matches for the inputted details.
    private fun noMatchAlert(){
        val builder = AlertDialog.Builder(this).create()
        builder.setTitle("No matches found")
        builder.setMessage("There are no birds with this combination of features at Intaka Island." +
                "Please recheck your selected options.")
        // The alert is built step by step, with the title at the top and message in the alert first set.

        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { dialog, which -> dialog.dismiss() }
        // A single button labeled ok is implemented on the alert to be used by the user to dismiss the alert.
        builder.show() // once the alert is fully developed, it is displayed to the user.
        // the following four lines are used to set the position of the "Ok" button in the alert to be in the middle
        // at the bottom of the alert.
        val btnPositive = builder.getButton(AlertDialog.BUTTON_POSITIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 20f
        btnPositive.layoutParams = layoutParams

    }



}