package com.example.birdy
/* This class implements a search screen with a list of all the birds at Intaka Island, either using their common or
   scientific name depending on the user's choice. Additionally, it provides a search bar for the user to utilise in
   searching for a specific bird.
   * Taahir Suleman - SLMTAA007
   * 20/08/2023
 */
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.birdy.databinding.ActivitySearchBinding

class SearchActivity :  ActivityBarFunctions() {// The class inherits ActivityBarFunctions for Action Bar functionality

    lateinit var binding: ActivitySearchBinding // A means of referencing the search activity's corresponding generated class.
    private var birdNameList: ArrayList<String> = ArrayList()
    private var scientficNameList: ArrayList<String> = ArrayList()


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        // This method is invoked when the search screen is opened to invoke the appropriate methods for implementing
        // the functionality of the search screen and the task bar.
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater) //generates the ActivitySearchBinding class in order to access the layout
        setContentView(binding.root) // Sets the view to the search activity
        setUpTab() // sets the search button on the task bar to be selected.
        populateLists()
        nameList(birdNameList, "Name")
        radioButtons(birdNameList, scientficNameList)
        super.setTaskbarBtns() // This method is used to enable to functionality of the task bar buttons.
    }
    private fun setUpTab(){
        // This method is invoked to make the task bar display the search button as selected when the user is on the
        // search screen.
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        tabLayout.getTabAt(1)?.select() // set the second button on the task bar to be selected.
    }

    private fun populateLists(){
        // This method is used to retrieve all the bird names from the Bird_Info table ordered alphabetically
        // by common names and by scientific names and to populate the associated array list accordingly.
        val db = BirdDatabase.getDatabase(this)
        // Run the two queries to get all entries in Bird_Info table, first ordered alphabetically by common names and then
        // ordered alphabetically by scientific names.
        val commonBirdList = db.birdInfoDao().getAllBirdsCommon()
        val scientificBirdList = db.birdInfoDao().getAllBirdsScientific()
        // For each entry returned by our queries we add that entry's BirdName or ScientificName field to its associated
        // array list respectively.
        for (bird in commonBirdList){
            birdNameList.add(bird.BirdName)
        }
        for (bird in scientificBirdList){
            scientficNameList.add(bird.ScientificName)
        }
    }

    private fun radioButtons(birdNameList: ArrayList<String>, scientificNameList: ArrayList<String>){
        // This method sets the functionality of the radio buttons to change the list of names between displaying
        // common names or scientific names depending on which radio button has been selected.
        val radioCommon = findViewById<RadioButton>(R.id.radio_common)
        val radioScientific = findViewById<RadioButton>(R.id.radio_scientific)
        val searchBar = findViewById<SearchView>(R.id.search_bar)
        radioCommon.setOnClickListener {
            // When the common name radio button is clicked, the bird list is set to display their common names, the
            // other radio button is unclicked, and the search bar is cleared.
            nameList(birdNameList, "Name")
            radioScientific.isChecked = false
            searchBar.setQuery("", false)
            searchBar.clearFocus()
        }
        radioScientific.setOnClickListener {
            // When the scientific name radio button is clicked, the bird list is set to display their common names, the
            // other radio button is unclicked, and the search bar is cleared.
            nameList(scientificNameList, "ScientificName")
            radioCommon.isChecked = false
            searchBar.setQuery("", false)
            searchBar.clearFocus()
        }
    }

    private fun nameList(nameList: ArrayList<String>, nameType: String){
        // This method is used to set the given array list to be displayed on the search screen's list view,
        // and implement the functionality of opening the associated bird information screen when a bird name is clicked.
        val birdListView = findViewById<ListView>(R.id.bird_list)

        //This listAdapter below is an ArrayAdapter, enabling the strings in the nameList array list to be displayed
        // on the associated list view.
        val listAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, nameList
        )
        birdListView.adapter = listAdapter // Set the ArrayAdapter of bListView to the above adapter.

        birdListView.setOnItemClickListener { parent, view, position, id ->
            // When a bird name is clicked, the bird information screen is opened and the bird's name is sent through
            // the associated intent.
            for(i in 0..nameList.size){
                if (position == i) {
                    // for every name in the list, if it is clicked, its name is retrieved using the adapter and sent
                    // through the intent used to open the bird info screen.
                    val value = birdListView.adapter.getItem(position).toString()
                    val intent = Intent(applicationContext, BirdInformationDisplay::class.java)
                    intent.putExtra(nameType, value)
                    if(nameType == "Name")
                        intent.putExtra("ScientificName", "")
                    else
                        intent.putExtra("Name", "")
                    startActivity(intent)
                }
            }
        }
        binding.birdList.adapter = listAdapter
        searchBar(nameList, listAdapter)

    }

    private fun searchBar(nameList: ArrayList<String>, listAdapter: ArrayAdapter<String>){
        // this method sets the functionality of the search bar to dynamically change according to what has been
        // inputted on the search bar by the user.
        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            //The above line sets a listener to query the input for the search bar when there is new input.
            override fun onQueryTextSubmit(query: String?): Boolean {
                // When the inputted query is submitted, the keyboard drops down and the focus is returned to the screen.
                binding.searchBar.clearFocus()
                binding.birdList.requestFocus()
                if(nameList.contains(query)){ //
                    listAdapter.filter.filter(query)
                    // Modify the search results based on the input submitted by the search bar.

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                // Modify the search results based on the current input in the search bar

                return false
            }

        })
    }


}

