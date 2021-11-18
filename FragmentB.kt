package com.example.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

interface OnClearButtonListener {
    fun onClearButtonListener(text: String)
}

class FragmentB : Fragment() {
    private lateinit var button2: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button2 = view.findViewById(R.id.button2)
        textView = view.findViewById(R.id.textView)
        button2.setOnClickListener() {clearText()}

    }
    fun touchText (data: String) {
        if (data == ""){
            shToast(String.format(resources.getString(R.string.plIn)))
        }
        else{
            textView.text = String.format(data)
        }
    }
    private fun shToast(text:String){
        val toast = Toast.makeText(this.activity, text, Toast.LENGTH_LONG)
        toast.show()
    }
    private fun clearText (){
        if (textView.text == String.format(resources.getString(R.string.empty))){
            shToast(String.format(resources.getString(R.string.inpF)))
        }
        textView.text = String.format(resources.getString(R.string.empty))
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentB().apply {
                arguments = Bundle().apply {

                }
            }
    }
}