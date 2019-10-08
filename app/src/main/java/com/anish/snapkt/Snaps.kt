package com.anish.snapkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class Snaps : AppCompatActivity() {
    var mAuth= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater =menuInflater
        inflater.inflate(R.menu.snaps_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.CreateSnap){
            val intent=Intent(this,Photo::class.java)
            startActivity(intent)
        }
        if(item?.itemId==R.id.LogOut){
            mAuth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mAuth.signOut()
    }
}
