package com.example.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.Error

class FragmentA : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.spinner)
        button = view.findViewById(R.id.button)
        button.setOnClickListener() {
            val listener = activity as OnClearButtonListener?
            writeToDB(spinner.selectedItem.toString())
            listener?.onClearButtonListener(spinner.selectedItem.toString()) }
        setAdapter()
    }

    private fun writeToDB(text: String){
        try{
            val db = this.context?.let { DBPart(it, null) }
            db?.addData(text)
            Toast.makeText(this.context, text + " added to database", Toast.LENGTH_LONG).show()
        }
        catch (e: Error){
            Toast.makeText(this.context, "Error adding to DB", Toast.LENGTH_LONG).show()
        }
    }

    private fun setAdapter(){
        val adapter = this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.language,
                android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentA().apply {
                arguments = Bundle().apply {
                }
            }
    }
}