package com.example.designing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log_in.*

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        button.setOnClickListener {
            /*val i= Intent(this, MapsActivity2::class.java)
            startActivity(i)*/
        }
        signup.setOnClickListener {
            val i= Intent(this, SignUp::class.java)
            startActivity(i)
        }

    }
}