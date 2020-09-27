package ru.zorin.messenger.repositories

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ru.zorin.messenger.fragments.chat_fragment.ChatListener
import ru.zorin.messenger.model.Message
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatRepository(var database: FirebaseDatabase,var sharedPreferences: SharedPreferences,var mAuth : FirebaseAuth) {

    var nextNumber :Int = 0

    lateinit var chatIdref:DatabaseReference
    lateinit var messageRef:DatabaseReference

    lateinit var listener : ChatListener

    fun logOut(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("login", "")
        editor.putInt("id",-1)
        editor.apply()
    }

    fun getChatId(){
        chatIdref = database.getReference("chatId")
        chatIdref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nextNumber = dataSnapshot.getValue(Int::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadChat(){
        messageRef = database.getReference("mess")
        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list :MutableList<Message> = ArrayList()
                for(next in dataSnapshot.children.toList()){
                    var messege = next.child("messege").getValue(String::class.java)
                    var username = next.child("username").getValue(String::class.java)
                    var userId = next.child("userId").getValue(Int::class.java)
                    var time = next.child("time").getValue(String::class.java)

                    list.add(Message(messege!!,username!!,userId!!,time!!))
                }

                listener.showChat(list)
            }
        })
    }

    fun sendMessage(message:String){
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)


        var mess = Message(message,sharedPreferences.getString("login","").toString(),sharedPreferences.getInt("id",0),dateText)
        messageRef.child("$nextNumber"+"${mAuth.currentUser!!.uid.substring(0..3)}").setValue(mess)
        chatIdref.setValue(++nextNumber)
    }

    fun getUserId():Int{
        return sharedPreferences.getInt("id",0)
    }

    fun getLogin(): String {
        return sharedPreferences.getString("login","")!!
    }
}