package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), OnClearButtonListener {
    private lateinit var buttonOpen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonOpen = findViewById(R.id.Activity2Btn)
        buttonOpen.setOnClickListener(){
            val intent = Intent(this, DBActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onClearButtonListener( data: String) {
        val fragmentManager = supportFragmentManager
        val fragmentB = fragmentManager.findFragmentById(R.id.fragment_b) as FragmentB?
        fragmentB?.touchText(data)
    }
}