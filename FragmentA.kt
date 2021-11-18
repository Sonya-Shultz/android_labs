package com.example.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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
            listener?.onClearButtonListener(spinner.selectedItem.toString()) }
        setAdapter()
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