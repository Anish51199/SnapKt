package com.anish.snapkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ChooseUser : AppCompatActivity() {

    var chooseUserList :ListView ?=null
    var emails:ArrayList<String> ?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,emails)
        chooseUserList?.adapter=adapter

        FirebaseDatabase.getInstance().reference.child("users").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                 //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                 //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                 //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val email=p0.child("email").value as String
                emails?.add(email)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                 //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
