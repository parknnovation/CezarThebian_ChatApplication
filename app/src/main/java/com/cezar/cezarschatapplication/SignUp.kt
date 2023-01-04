package com.cezar.cezarschatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edit_name: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_password: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

       supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edit_name = findViewById(R.id.edit_name)
        edit_email = findViewById(R.id.edit_email)
        edit_password = findViewById(R.id.edit_password)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        buttonSignUp.setOnClickListener {
            val name = edit_name.text.toString()
            val email = edit_email.text.toString()
            val password = edit_password.text.toString()

            signUp(name,email,password)
        }
    }
    private fun signUp(name:String, email:String, password:String){
        // logic of creating a user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)


                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}