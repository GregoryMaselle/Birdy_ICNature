package com.example.birdy
/*  This activity provides an interface with which Bird_Features entries may be inserted into the database.
    Taahir Suleman - SLMTAA007
    28/08/2023
 */
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*  THIS CLASS WAS USED IN POPULATING THE BIRD_INFO AND BIRD_FEATURES TABLES IN THE DATABASE WITH THE AID OF THE BirdDB
    CLASS AND SCREEN, MODIFIED ACCORDINGLY FOR INSERTING INTO EACH RESPECTIVE TABLE.
    THIS CLASS IS NO LONGER IN USE. IT IS MERELY HERE TO DEMONSTRATE WHAT WE USED TO POPULATE THE BIRD_INFO AND
    BIRD_FEATURES TABLES IN OUR DATABASE, BEFORE SWITCHING OUR DATABASE IMPLEMENTATION TO A ROOM DATABASE.
    THE CURRENT IMPLEMENTATION WHICH IS IN USE IS GIVEN BY THE BirdDatabase CLASS, WITH THE ASSOCIATED DAOs AND
    ENTRY CLASSES.
 */
class DbActivity: AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        addEntry()
    }

    private fun addEntry(){
        // This method inserts the provided bird name and features into the Bird_Features table when the add bird button
        // is clicked.
        val btnAdd = findViewById<Button>(R.id.addBird)
        val enterName = findViewById<EditText>(R.id.enterName)
        val enterBillColour = findViewById<EditText>(R.id.enterBillColour)
        val enterBillShape = findViewById<EditText>(R.id.enterBillShape)
        val enterHeight = findViewById<EditText>(R.id.enterHeight)
        val enterUnderparts = findViewById<EditText>(R.id.enterUnderparts)
        val enterUpperparts = findViewById<EditText>(R.id.enterUpperparts)

        btnAdd.setOnClickListener{
            val db = BirdDB(this, null)

            // retrieve the inputted name and features from the associated edit texts.
            val name = enterName.text.toString()
            val bColour = enterBillColour.text.toString()
            val bShape = enterBillShape.text.toString()
            val height = enterHeight.text.toString()
            val underparts = enterUnderparts.text.toString()
            val upperparts = enterUpperparts.text.toString()

            // insert the given name and features into the Bird_Features table.
            db.addFeatures(name, bColour, bShape, height, underparts, upperparts)
            Toast.makeText(this, "INSERTION SUCCESSFUL", Toast.LENGTH_SHORT).show()

            //Clear all the edit texts.
            enterName.text.clear()
            enterBillColour.text.clear()
            enterBillShape.text.clear()
            enterHeight.text.clear()
            enterUnderparts.text.clear()
            enterUpperparts.text.clear()
        }
    }
}