package ru.zorin.messenger.repositories

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ru.zorin.messenger.fragments.chat_fragment.ChatListener
import ru.zorin.messenger.fragments.users_fragment.UserListener
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.model.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatRepository(var database: FirebaseDatabase,var sharedPreferences: SharedPreferences,var mAuth : FirebaseAuth) {

    var nextNumber :Int = 0
    var nextDialogId :Int = 0

    lateinit var chatIdref:DatabaseReference
    lateinit var messageRef:DatabaseReference
    lateinit var usersRef:DatabaseReference
    lateinit var dialogIdRef:DatabaseReference
    lateinit var dialogsRef:DatabaseReference
    lateinit var lastQuery: Query

    lateinit var listener : ChatListener
    lateinit var userListener : UserListener

    lateinit var usersRefEvent:ValueEventListener
    lateinit var chatIdrefEvent:ValueEventListener
    lateinit var lastQueryEvent:ValueEventListener
    lateinit var dialogsRefEvent:ValueEventListener

    var list :MutableList<User> = ArrayList()

    fun logOut(){
        list.clear()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("login", "")
        editor.putInt("id",-1)
        editor.apply()

        clearEvents()
    }

    fun getChatId(){
        chatIdref = database.getReference("chatId")
        chatIdrefEvent = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nextNumber = dataSnapshot.getValue(Int::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        chatIdref.addValueEventListener(chatIdrefEvent)
    }

    fun checkDialog(friendLogin:String){
        dialogsRef = database.getReference("dialogs")
        dialogsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dialogId:Int = -1
                dialogId = dataSnapshot.child("${getLogin()}:$friendLogin").getValue(Int::class.java)?:-1

                dialogsRef.removeEventListener(this)
                if(dialogId<0){
                    createDialog(friendLogin)
                }else{
                    userListener.openChat(dialogId,friendLogin)
                }
            }
        })
    }

    fun createDialog(friendLogin:String){
        nextDialogId++
        dialogsRef.child("${getLogin()}:$friendLogin").setValue(nextDialogId)
        dialogsRef.child("$friendLogin:${getLogin()}").setValue(nextDialogId)
        dialogIdRef.setValue(nextDialogId)
        userListener.openChat(nextDialogId,friendLogin)
    }

    fun getDialogId(){
        dialogIdRef = database.getReference("dialogId")
        dialogIdRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nextDialogId = dataSnapshot.getValue(Int::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadChat(dialogId:Int){
        messageRef = database.getReference("mess")
        messageRef.child("$dialogId").addValueEventListener(object : ValueEventListener {
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

    fun sendMessage(message:String,dialogId: Int){
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("MM:dd HH:mm:ss", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)

        var mess = Message(message,sharedPreferences.getString("login","").toString(),sharedPreferences.getInt("id",0),dateText)
        messageRef.child("$dialogId").child("").child("$nextNumber"+"${mAuth.currentUser!!.uid.substring(0..3)}").setValue(mess)
        chatIdref.setValue(++nextNumber)
    }

    fun getUserId():Int{
        return sharedPreferences.getInt("id",0)
    }

    fun getLogin(): String {
        return sharedPreferences.getString("login","")!!
    }

    fun loadUsers(){
        usersRef = database.getReference("users")
        usersRefEvent = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(next in dataSnapshot.children.toList()){
                    var login = next.key
                    var userId = next.child("id").getValue(Int::class.java)

                    if(!login.equals(getLogin())) {

                        getDialogIdForLastMessage(login!!,userId!!)
                    }
                }

            }
        }
        usersRef.addValueEventListener(usersRefEvent)
    }



fun getDialogIdForLastMessage(friendLogin:String,userId:Int){
    dialogsRef = database.getReference("dialogs")

    dialogsRefEvent = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {

        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var dialogId:Int = -1
            dialogId = dataSnapshot.child("${getLogin()}:${friendLogin}").getValue(Int::class.java)?:-1

            if(dialogId>0){
                getLastMessage(dialogId,friendLogin,userId)
            }else{

                var flag = true

                for (i in 0 until list.size){
                    if (list[i].login == friendLogin){
                        list[i] = User(friendLogin, userId,Message("","",0,"0"))
                        flag = false
                        break
                    }else{
                        flag = true
                    }
                }

                if (flag){
                    list.add(User(friendLogin, userId,Message("","",0,"0")))
                }

                userListener.showUsers(list)
            }

            dialogsRef.removeEventListener(this)
        }
    }
    dialogsRef.addValueEventListener(dialogsRefEvent)
}

   var listQueries: MutableList<Query> = ArrayList()
   var listEvents: MutableList<ValueEventListener> = ArrayList()

fun getLastMessage(dialogId: Int,friendLogin:String,friendId:Int){
    lastQuery = database.getReference("mess").child("$dialogId").orderByKey().limitToLast(1);
    lastQueryEvent = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {

        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.children.toList().size > 0) {
                for (next in dataSnapshot.children.toList()) {
                    val messege = next.child("messege").getValue(String::class.java)
                    val username = next.child("username").getValue(String::class.java)
                    val userId = next.child("userId").getValue(Int::class.java)
                    val time = next.child("time").getValue(String::class.java)

                    var flag = true

                    for (i in 0 until list.size) {
                        if (list[i].login == friendLogin) {
                            list[i] = User(
                                friendLogin,
                                friendId,
                                Message(messege!!, username!!, userId!!, time!!)
                            )
                            flag = false
                            break
                        } else {
                            flag = true
                        }
                    }

                    if (flag) {
                        list.add(
                            User(
                                friendLogin,
                                friendId,
                                Message(messege!!, username!!, userId!!, time!!)
                            )
                        )
                    }

                    userListener.showUsers(list)
                }
            }else{
                var flag = true

                for (i in 0 until list.size){
                    if (list[i].login == friendLogin){
                        list[i] = User(friendLogin, friendId,Message("","",0,"0"))
                        flag = false
                        break
                    }else{
                        flag = true
                    }
                }

                if (flag){
                    list.add(User(friendLogin, friendId,Message("","",0,"0")))
                }
                userListener.showUsers(list)
            }
        }
    }

    lastQuery.addValueEventListener(lastQueryEvent)

    listQueries.add(lastQuery)
    listEvents.add(lastQueryEvent)
}


    private fun clearEvents(){
        for(i in 0 until listEvents.size){
            listQueries[i].removeEventListener(listEvents[i])
        }
    }
}