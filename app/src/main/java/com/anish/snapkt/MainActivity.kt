package com.anish.snapkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    var editMail:EditText ?= null
    var editPassword:EditText ?= null
    var mAuth=FirebaseAuth.getInstance()
    var database=FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editMail=findViewById(R.id.Email)
        editPassword=findViewById(R.id.Password)
        if(mAuth.currentUser!=null){
           LogIn()
        }
    }
    fun Clicked(view : View){
        mAuth.signInWithEmailAndPassword(editMail?.text.toString(), editPassword?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    LogIn()

                } else {
                    // If sign in fails, SignUp
                    mAuth.createUserWithEmailAndPassword(editMail?.text.toString(), editPassword?.text.toString())
                        .addOnCompleteListener(this){task ->
                           if(task.isSuccessful){
                               //Update database
                               database.reference.child("users").child(task.result?.user?.uid.toString()).child("email").setValue(editMail?.text.toString())

                               LogIn()
                           }else
                               Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                        }
                }

            }
    }
    fun LogIn() {
        val intent = Intent(this, Snaps::class.java)
        startActivity(intent)
        }
    }






