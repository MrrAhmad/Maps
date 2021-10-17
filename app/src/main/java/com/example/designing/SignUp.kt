package com.example.designing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        buttons.setOnClickListener {
//            val i= Intent(this, MapsActivity2::class.java)
//            startActivity(i)
        }
        signin.setOnClickListener {
            val i= Intent(this, LogIn::class.java)
            startActivity(i)
        }
    }
}