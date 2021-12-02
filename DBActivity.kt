package com.example.lab1

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DBActivity : AppCompatActivity() {
    private lateinit var textView: TextView;
    private lateinit var buttonDelete: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbactivity)

        textView = findViewById<TextView>(R.id.textView).apply {
            var data = readData(this.context)
            text = data
        }
        buttonDelete = findViewById<Button>(R.id.DeleteDBBtn)
        buttonDelete.setOnClickListener(){
            try{
                val db = DBPart(this, null)
                db.deleteMainTable()
                textView.apply {
                    var data = readData(this.context)
                    text = data
                }
                Toast.makeText(this, "Database is now empty", Toast.LENGTH_LONG).show()
            }
            catch(e: Error){
                Toast.makeText(this, "Error in database", Toast.LENGTH_LONG).show()
            }
        }
    }
    @SuppressLint("Range")
    private fun readData(context: Context): String {
        val db = context.let { DBPart(it, null) }
        var cursor = db?.getData()
        var data = ""
        if (cursor != null) {
            while (cursor.moveToNext()){
                data+=cursor.getString(cursor.getColumnIndex("selection"))+"\n"
            }
        }
        if (data == "") data = getString(R.string.no_data_in_db)
        return data
    }
}