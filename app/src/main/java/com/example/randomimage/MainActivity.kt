package com.example.randomimage

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.randomimage.databinding.ActivityMainBinding


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var currentImage = ""
    private var currentText = ""
    private val images = arrayOf(
        R.drawable.pic_one,
        R.drawable.pic_two,
        R.drawable.pic_three,
        R.drawable.pic_four,
        R.drawable.pic_five
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("RandomImagePreferences", MODE_PRIVATE)

        // Restore state
        restoreState()



        // Wiring up the change button
        binding.changeButton.setOnClickListener() {
            Log.d(TAG, "Clicking Button")
            setRandomImage(binding.imageView)
        }
        // Initialize random image
        if (currentImage == ""){
            setRandomImage(binding.imageView)
        }


        //Save user's input
        binding.apply {
            userInput.doOnTextChanged() { text, _, _, _ ->
                currentText = text.toString()
                Log.d("MainActivity", currentText)
            }
        }
    }

    override fun onDestroy() {
        val editor = sharedPreferences.edit()
        editor.putString("currentImage", currentImage)
        editor.putString("currentText", currentText)
        editor.commit()
        Log.d(TAG, "onDestroy called")
        super.onDestroy()



    }

    // Function to set a random image
    private fun setRandomImage(imageView: ImageView) {
        val randomIndex = (images.indices).random()
        currentImage = images[randomIndex].toString()
        imageView.setImageResource(images[randomIndex])
    }

    private fun restoreState() {
        Log.d(TAG, "Restoring state")
        // Retrieve and set the stored image and text
        val savedImage = sharedPreferences.getString("currentImage", "")
        val savedText = sharedPreferences.getString("currentText", "")
        if (savedImage!!.isNotEmpty()) {
            Log.d(TAG, savedImage)
            currentImage = savedImage
            val resId = resources.getIdentifier(savedImage, "drawable", packageName)
            if (resId != 0) { // Resource exists
                binding.imageView.setImageResource(resId)
            }
        }
        if (savedText!!.isNotEmpty()) {
            Log.d(TAG, savedText)
            currentText = savedText
            binding.userInput.setText(savedText)
        }
    }


}