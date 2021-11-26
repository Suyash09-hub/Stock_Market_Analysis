package com.example.stockmoney

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signin_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()
            Log.d("Login", "Attempt login with email/pw: $email/****")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    // else
                    Log.d("Main", "Successfully Login")
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to Login ${it.message}")
                    val toast =
                        Toast.makeText(this, "Failed to Login ${it.message}", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
        }
        forgot_password_login.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            builder.setTitle("Enter Your Email")
            builder.setCancelable(false)
            val email = view.findViewById<EditText>(R.id.forgot_password_email)
            builder.setPositiveButton("Submit", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()
        }
    }

    private fun forgotPassword(email: EditText) {
        if (email.text.toString().isEmpty()) {
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            return
        }

        FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Sent.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Please Enter valid email", Toast.LENGTH_SHORT).show()

            }
    }
}