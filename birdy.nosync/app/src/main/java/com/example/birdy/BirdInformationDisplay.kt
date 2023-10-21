package com.example.birdy
/*
    This class is used to display the information and image of a particular bird. The details of the
    bird are fetched from a database and displayed in a scroll view. The image of the bird is fetched
    from an off-site firebase storage and displayed on the screen.
    The class inherits ActivityBarFunctions for Activity Bar functionality
    *Gregory Maselle - MSLGRE001
    *16/08/2023
 */
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle;
import android.provider.Settings.Global.getString
import android.widget.*
import com.example.birdy.databinding.BirdInfoDisplayBinding
import com.google.firebase.storage.FirebaseStorage


class BirdInformationDisplay : ActivityBarFunctions() {
    lateinit var binding: BirdInfoDisplayBinding
    @SuppressLint("Range")
    /*
        This function is called when the screen needs to be displayed. It creates the
        BirdInformationDisplay activity. It receives bird information via an intent and then
        uses that information to retrieve and display data on that bird.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BirdInfoDisplayBinding.inflate(layoutInflater)//Used to access the layout
        setContentView(binding.root) // Display the content as prescribed in the XML file bird_info_display.xml layout
        clearTabs() // Calls the super method clearTabs()
        backBtn()
        /*
            Lines 37 and 38 are fetching data from the intent.
            Lines 40 to 48 are identifying and giving variable references to various views
            in the BirdInformationDisplayActivity
         */
        var birdName = intent.getStringExtra("Name")
        val scientificName = intent.getStringExtra("ScientificName")
        val addSpottingbtn = findViewById<ImageButton>(R.id.addSpottingbtn)
        val nameTxt = findViewById<TextView>(R.id.birdDescriptor)
        val scNameTxt = findViewById<TextView>(R.id.birdScientific)
        val billSTxt = findViewById<TextView>(R.id.billShapeTxt)
        val billCTxt = findViewById<TextView>(R.id.billColourTxt)
        val heightTxt = findViewById<TextView>(R.id.heightTxt)
        val underpartsTxt = findViewById<TextView>(R.id.underpartsTxt)
        val upperpartsTxt = findViewById<TextView>(R.id.upperpartsTxt)
        val descriptionTxt = findViewById<TextView>(R.id.descriptionTxt)

        //Get an instance of our database object
        val db = BirdDatabase.getDatabase(this)
        /*
            In the case that this screen was called from SearchActivity.kt and the search was
            done via scientific name, the birdName will be fetched from the database using
            the scientific name.
         */
        if (birdName == ""){
            //Run the getBirdName query in BirdInfoDAO to get the common name of a bird via their
            //scientfic name.
            val bird = db.birdInfoDao().getBirdName(scientificName!!)
            birdName = bird[0].BirdName
        }
        fetchImage(birdName) // A method to fetch and display the bird image.
        //Run a query to get the entry for a bird that was searched for from Bird_Info
        val viewedBird = db.birdInfoDao().getBirdDesc(birdName!!)
        /*
            A query is run in BirdFeaturesDAO to get the bird features
            for the specific bird.
         */
        val viewedBirdFeatures = db.birdFeaturesDao().getBirdFeatures(birdName)
        val billColour = viewedBirdFeatures[0].BillColour
        val billShape = viewedBirdFeatures[0].BillShape
        val height = viewedBirdFeatures[0].Height
        val upperColour = viewedBirdFeatures[0].UpperpartsColour
        val underColour = viewedBirdFeatures[0].UnderpartsColour
        val desc = viewedBird[0].BirdDescription
        /*
            Set the respective views in the activity to display the desired
            texts.
         */
        nameTxt.setText(viewedBird[0].BirdName)
        scNameTxt.setText(viewedBird[0].ScientificName)
        billCTxt.setText(getString(R.string.billColourDisplay, billColour))
        billSTxt.setText(getString(R.string.billShapeDisplay, billShape))
        heightTxt.setText(getString(R.string.heightDisplay, height))
        underpartsTxt.setText(getString(R.string.underpartsDisplay, underColour))
        upperpartsTxt.setText(getString(R.string.upperpartsDisplay, upperColour))
        descriptionTxt.setText(desc)
        super.setTaskbarBtns() // This method is used to enable to functionality of the task bar buttons.
        setAddSpottingListener(addSpottingbtn,birdName)//A method to encapsulate the button listener for the addspottings button.
    }
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
    /*
        This method encapsulates the code that fetches the bird image from
        the firebase storage. It accepts the bird name as an argument and fetches
        the bird's image.
     */
    private fun fetchImage(birdName: String?){
        //This gets a reference to the object in the firebase storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$birdName.jpg")
        Toast.makeText(this, "getting image",Toast.LENGTH_SHORT).show()
        /*This uses the object reference to download it, with a max download size
         of 5 Mib. It is also has listeners.
         addOnSuccessListener contains code that will run if the download is successful
         addOnFailureListener contains code that will run if the download fails.
         */
        storageRef.getBytes(1024*1024*5).addOnSuccessListener {
            //The downloaded bytes are stored as a bitmap, and the imageView
            //is set to this bitmap.
           val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            binding.birdView.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this, "failed to get image from database",Toast.LENGTH_SHORT).show()
        }
    }
    /*
        This method contains the code that is run when the user clicks on
        the addSpotting ImageButton. It accepts the ImageButton, and the birdname
        as arguments. If the button is selected, add the birdname to an intent
        and start the AddSpottingActivity.
     */
    private fun setAddSpottingListener(addSpottingbtn: ImageButton, birdName : String?){
        addSpottingbtn.setOnClickListener {
            val intent  = Intent(applicationContext, AddSpottingActivity::class.java)
            intent.putExtra("Name",birdName)
            startActivity(intent)
        }
    }

}