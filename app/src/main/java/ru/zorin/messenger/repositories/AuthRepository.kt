package ru.zorin.messenger.repositories

import android.content.SharedPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.zorin.messenger.fragments.auth_fragment.AuthListener
import ru.zorin.messenger.fragments.registration_fragment.RegListener

class AuthRepository(var database: FirebaseDatabase,var sharedPreferences: SharedPreferences) {

    private var maxId:Int = 0

    lateinit var listener : AuthListener

    lateinit var regListener : RegListener

    fun singIn(login:String, password:String){
        val myRef = database.getReference("users")

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(login).child("password").getValue(String::class.java) == password){
                    myRef.removeEventListener(this)
                    listener.onLoginSuccess(login, snapshot.child(login).child("id").getValue(Int::class.java)!!)
                }else{
                    listener.onLoginError()
                }

                myRef.removeEventListener(this)
            }
        })

    }

    fun setMaxId(id:Int){
        val myRef = database.getReference("maxId")
        myRef.setValue(id)
    }

    fun getMaxId(){
        val myRef = database.getReference("maxId")
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
               maxId = snapshot.getValue(Int::class.java)!!
            }
        })
    }

    fun registerUser(login:String, password:String, email:String){
        val checkRef = database.getReference("users")

        checkRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(login).child("password").getValue(String::class.java) != null){
                    regListener.onRegistrationError()
                }else{
                    val myRef = database.getReference("users")
                    myRef.child(login).child("password").setValue(password)
                    myRef.child(login).child("id").setValue(maxId++)
                    myRef.child(login).child("email").setValue(email)
                    setMaxId(maxId)
                    regListener.onRegistrationSuccess()
                }

                checkRef.removeEventListener(this)
            }
        })
    }

    fun saveUserInfo(login:String,id:Int){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("login", login)
        editor.putInt("id",id)
        editor.apply()
    }
}