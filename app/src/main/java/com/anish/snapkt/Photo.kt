package com.anish.snapkt

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class Photo : AppCompatActivity() {
    var images :ImageView ?= null
    var Message:EditText ?= null
    var next :Button ?=null
    var choose :Button ?=null
    val imageName= UUID.randomUUID().toString()+".jpg"

    fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        images=findViewById(R.id.imageview)
        Message=findViewById(R.id.editTextMessage)

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    fun chooseImageClicked(view: View){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                images?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun nextClicked(view: View){
        // Get the data from an ImageView as bytes
        images?.isDrawingCacheEnabled = true
        images?.buildDrawingCache()
        val bitmap = (images?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        var uploadTask = FirebaseStorage.getInstance().getReference().child("images").child(imageName).putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(this,"Upload Failed",Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {taskSnapshot->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
           // val downloadUrl=taskSnapshot.metadata?.reference?.downloadUrl
         //   Log.i("Msg->",downloadUrl.toString())
            var i =Intent(this,ChooseUser::class.java)
            startActivity(i)
        }
    }
}
