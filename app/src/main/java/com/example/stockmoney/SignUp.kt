package com.example.stockmoney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signup_button.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {
        val email = email_edittext_signup.text.toString()
        val password = password_edittext_signup.text.toString()
        val username = username_edittext_signup.text.toString()
        val phonenumber = phonenumber_edittext_signup.text.toString()

        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phonenumber.isEmpty()) {
            Toast.makeText(this, "Please enter above requirements", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity", "Email: " + email)
        Log.d("RegisterActivity", "Password: " + password)
        Log.d("RegisterActivity", "Username: " + username)
        Log.d("RegisterActivity", "PhoneNumber: " + phonenumber)

        // Firebase Authentication to create a user with email and password
        val db = FirebaseFirestore.getInstance()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successfull
                Log.d(
                    "RegisterActivity",
                    "Successfully created user with uid: ${it.result?.user?.uid}"
                )
                // Create a new user with a first and last name

                // Create a new user with a first and last name
                val user = hashMapOf(
                    "Phone Number" to phonenumber,
                    "Username" to username,
                )
                db.collection("UsersDetails").document("${it.result?.user?.uid}")
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(
                            "RegisterActivity",
                            "Details added with ID"
                        )
                        val intent = Intent(this, MainActivity2()::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "RegisterActivity",
                            "Error adding document",
                            e
                        )
                    }
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    fun openLogin(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}