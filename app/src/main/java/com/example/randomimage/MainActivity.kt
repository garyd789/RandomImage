package com.example.randomimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import com.example.randomimage.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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


        //Wiring up the change button
        binding.changeButton.setOnClickListener() {
            Log.d(TAG, "Clicking Button")
            setRandomImage(binding.imageView)
        }
        // Initialize random image
        setRandomImage(binding.imageView)

        //Save user's input
        binding.apply {
            userInput.doOnTextChanged() { text, _, _, _ ->
                val input = text.toString()
                Log.d("MainActivity", input)
            }
        }
    }

    // Function to set a random image
    private fun setRandomImage(imageView: ImageView) {
        val randomIndex = (images.indices).random()
        imageView.setImageResource(images[randomIndex])
    }


}