package com.example.birdy

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
/*
    This class is used to display the results obtained from the FindBirdByFeatures class
    The results are added to a list and clicking on a particular item, will open the birdInformationDisplay for the item
    * Prashanth Padiachy - PDCPRA001
    * 10/09/2023
 */
class BirdCharacteristicSearchResults : ActivityBarFunctions() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_characteristic_search_results)
        setTaskbarBtns()
        clearTabs()
        backBtn()
        getResults()
    }
    //This method is used to display the results found from the FindBirdByFeatures screen
    //It displays the results as a list and sets it so that when a particular result is selected, it links to the birdInformationDisplay, displaying the information about the bird.
    private fun getResults(){
        val resultList = (intent.getSerializableExtra("resultList") as? ArrayList<String>)!!
        val titleTxt = findViewById<TextView>(R.id.title)
        val birdList = findViewById<ListView>(R.id.name_list)

        val birdsAdapter = ArrayAdapter<String>(this, R.layout.activity_char_result_row, resultList)

        birdList.adapter = birdsAdapter

        birdList.setOnItemClickListener { parent, view, position, id ->
            //This listens for clicks on the birdList ListView
            // If there is a click and it is the first entry in the ListView, then create the
            // BirdInformationDisplay activity. It takes you to the information page of the
            // bird in that entry.
            for(i in 0..resultList.size){
                if (position == i) {
                    val value = birdList.adapter.getItem(position).toString()
                    val intent = Intent(applicationContext, BirdInformationDisplay::class.java)
                    intent.putExtra("Name", value)
                    startActivity(intent)
                }
            }
        }}
    /*
        This method is used to enable the functionality of a back button in the
        activity.
     */
    private fun backBtn(){
        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }
    }
}