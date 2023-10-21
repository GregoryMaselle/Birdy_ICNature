package com.example.birdy

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import java.util.*
/*
    This class is used to add a new spotting entry to the user's spottings. It does this by requesting user input detailing a particular spotting
    and using the input to make an entry into the spotting database
    * Prashanth Padiachy- PDCPRA001
    * 05/09/2023
 */

class AddSpottingActivity : ActivityBarFunctions() { // The class inherits ActivityBarFunctions for Action Bar functionality
    //this method is run when the add spotting screen is displayed. It creates the layout, sets the screen to use the taskbar, captures user input and
    //updates the database accordingly
    override fun onCreate(savedInstanceState: Bundle?) { // A function that runs when an activity is created.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spotting) // Display the content as prescribed in the XML
        // file activity_add_spotting,xml
        clearTabs()
        super.setTaskbarBtns() // This method is used to enable to functionality of the task bar buttons.
        setInputs()
        mapBtn()
    }

//This function sets up the functionality of the spinners and calendar date selector input for when the user is adding a new spotting
    //it takes the inputted values on the ui and creates a BirdSpotted object which is then passed to the spottings page using an intent when the submit button is clicked
    private fun setInputs(){
        val birdName = intent.getStringExtra("Name")
        val commonText = findViewById<TextView>(R.id.birdCommonNameAnswer)
        commonText.setText(birdName)
        val addBtn = findViewById<Button>(R.id.addButton) // Create instance of a view for addBtn
        val sightCalendar = findViewById<CalendarView>(R.id.spottingCalendar)// Create instance of a view

        // for the CalendarView spottingCalendar

        var helpSpinnerOptions : Spinner = findViewById(R.id.HabitatSpinner) // Create instance of a view
        // for the spinner displaying habitats
        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.habitatlist, android.R.layout.simple_spinner_item)
        //The above line creates an ArrayAdapter which allows the Strings in habitatList (in resources)
        // to be displayed in their own view.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        //This line then sets the layout resource to create the drop down views
        // for each String item in habitatList
        helpSpinnerOptions.setAdapter(adapter)// set the adapter for the Spinner to our created arrayAdapter
        //thus giving the spinner access to the views containing the strings in habitatlist.

        //An OnDateChangeListener is set to listen for changes in the users selection of a date
        //in the calendarView. When a change is heard, this change is used to update the date
        //variable which contains the selected date.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date: String = day.toString()+"/"+(month+1).toString()+"/"+year.toString()
        sightCalendar.maxDate = c.timeInMillis
        sightCalendar.minDate = (c.timeInMillis-31556952000) // The number of ms in a year

        sightCalendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            date = dayOfMonth.toString()+"/"+(month+1).toString()+"/"+year.toString()
        })
        addBtn.setOnClickListener{ // A button listener listening for button presses on addBtn
            var zone = helpSpinnerOptions.getSelectedItem().toString() // This retrieves the selected
            // item from the spinner
            Toast.makeText(getApplicationContext(), "Spotting Added!",Toast.LENGTH_SHORT).show();
            // A short notification telling the user that the spotting was successfully added.
            var spot: BirdSpotted = BirdSpotted(birdName!!, date, zone)//A BirdSpotted object
            //is created that represents the spotting of the bird. It contains the name of the bird, the location
            //of the spotting and the habitat it was spot in.
            val intent = Intent(applicationContext, Spottings::class.java)// This creates an intent which will
            //open the Spottings activity.
            intent.putExtra("SpottedBird", spot)// The SpottedBird object is then added to the intent
            startActivity(intent) // The Spottings activity is started, with the edited intent.
        }

    }
//This function is used to set the functionality of the map button, when clicked the map screen is opened
    //and displays a map of the reserve with a grid over it to indicate the diffrent habitats
    private fun mapBtn(){
        val mapBtn = findViewById<Button>(R.id.map_btn)
        mapBtn.setOnClickListener{
            val intent = Intent(applicationContext, MapScreen::class.java)
            intent.putExtra("Source", "Spottings")//passes intent to MapScreen to indicate map with grid is needed
            startActivity(intent)
        }
    }
}