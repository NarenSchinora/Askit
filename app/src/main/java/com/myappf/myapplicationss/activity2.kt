package com.myappf.myapplicationss

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity2 : AppCompatActivity() {


    override fun onBackPressed() {
        val bi = AlertDialog.Builder(this)
        bi.setTitle("close Ask iT!")
        bi.setMessage("Do you really want to close the app?")
        bi.setPositiveButton("YES", { dialogInterface: DialogInterface, i: Int ->
            finish() })
        bi.setNegativeButton("NO" , { dialogInterface: DialogInterface, i: Int ->
             })
        bi.show()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2)
        val btn = findViewById<Button>(R.id.bt)

        btn.setOnClickListener {
            val itt = Intent(this,MainActivity::class.java)
            startActivity(itt)

        }
    }
}