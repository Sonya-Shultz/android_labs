package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnClearButtonListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClearButtonListener( data: String) {
        val fragmentManager = supportFragmentManager
        val fragmentB = fragmentManager.findFragmentById(R.id.fragment_b) as FragmentB?
        fragmentB?.touchText(data)
    }
}