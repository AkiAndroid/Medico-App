package com.example.medicoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicoapp.UserData.UserDataInfo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

class FirstTimeLogin : AppCompatActivity() {

    lateinit var Name:EditText
    lateinit var Email:EditText
    lateinit var Password:EditText

    lateinit var LogIn:Button

    lateinit var db:FirebaseDatabase
    lateinit var dbref:DatabaseReference

    lateinit var number:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time_login)

        number= intent.getStringExtra("number").toString()

        //Database Reference
         db =FirebaseDatabase.getInstance()

         dbref=db.getReference("users")

        Name=findViewById(R.id.Name)
        Email=findViewById(R.id.Email)
        Password=findViewById(R.id.Password)

        LogIn=findViewById(R.id.LogIn)



        LogIn.setOnClickListener {

            if(TextUtils.isEmpty(Name.text.toString()) || TextUtils.isEmpty(Email.text.toString()) || TextUtils.isEmpty(Password.text.toString())){
                Toast.makeText(applicationContext,"Enter all the fields",Toast.LENGTH_SHORT)
            }
            else{
                var userData=UserDataInfo(Name.text.toString(),Email.text.toString(),Password.text.toString())
                addUser(userData)
            }
        }

    }

    fun addUser(userData:UserDataInfo){

        dbref.addListenerForSingleValueEvent(object: ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {

                if(!snapshot.child(number).exists()){
                    dbref.child(number).setValue(userData).addOnCompleteListener(object :OnCompleteListener<Void>{
                        override fun onComplete(p0: Task<Void>) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}