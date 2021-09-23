package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var spinner: Spinner

    private fun shToast(text:String){
        val toast = Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG)
        toast.show()
    }
    fun touchText (view: View) {
        if (spinner.selectedItem == null){
            shToast(String.format(resources.getString(R.string.plIn)))
        }
        else{
            val countString = spinner.selectedItem.toString()
            textView.text = countString
        }
    }
    fun clearText (view: View){
        if (textView.text == String.format(resources.getString(R.string.empty))){
            shToast(String.format(resources.getString(R.string.inpF)))
        }
        textView.text = String.format(resources.getString(R.string.empty))
    }
    private fun setAdapter(){
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.language,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        spinner = findViewById(R.id.spinner)

        setAdapter()
    }

}