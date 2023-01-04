package com.cezar.cezarschatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edit_email: EditText
    private lateinit var edit_password: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edit_email = findViewById(R.id.edit_email)
        edit_password = findViewById(R.id.edit_password)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.buttonSignUp)


        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val email = edit_email.text.toString()
            val password = edit_password.text.toString()

            login(email,password);
        }
    }

    private fun login(email: String, password: String){
        // login for logging user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                //code for logging in user
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_SHORT).show()

                }
            }

    }
}