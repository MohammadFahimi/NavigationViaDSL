package com.mfahimi.demo.ui.main.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.mfahimi.demo.R
import com.mfahimi.demo.navigation.NavArguments

class DestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.destination_activity)
        val plantId: String? = intent?.getStringExtra(NavArguments.activity_parameters)
        
        Toast.makeText(this, "$plantId", Toast.LENGTH_SHORT).show()
    }
}