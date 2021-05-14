package com.example.medicoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var PhoneNum:EditText
    lateinit var Otp:EditText
    lateinit var getOtp:Button
    lateinit var verifyOtp:Button


    lateinit var mauth:FirebaseAuth

    lateinit var verficationId:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       PhoneNum=findViewById(R.id.phoneNumberEdit)
       Otp=findViewById(R.id.OtpEditTEXT)
       getOtp=findViewById(R.id.getOTPbutton)
       verifyOtp=findViewById(R.id.VerifyButton)

       mauth=FirebaseAuth.getInstance()

        getOtp.setOnClickListener{

            if(TextUtils.isEmpty(PhoneNum.text.toString())){
                PhoneNum.setError("Enter a Valid Phone Number")
            }

            else{
                var number:String="+91"+PhoneNum.text.toString()
                SendVerification(number)


            }

        }

    }


    fun SendVerification(number:String){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,TimeUnit.SECONDS,this,mcallBack)
    }

    var mcallBack:PhoneAuthProvider.OnVerificationStateChangedCallbacks=object:
        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verficationId=p0
        }


        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            var OtpCode: String? =p0.smsCode

            if(OtpCode !=null){
                Otp.setText(OtpCode)

                verifyCode(OtpCode)
            }
        }


        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(applicationContext,p0.message,Toast.LENGTH_SHORT)
        }

    }

    fun verifyCode(OtpCode:String){
        signInWithCredential(PhoneAuthProvider.getCredential(verficationId,OtpCode))

    }

    fun signInWithCredential(credential: PhoneAuthCredential){

        mauth.signInWithCredential(credential).addOnCompleteListener(object :OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful){

                }
            }

        })



    }
}