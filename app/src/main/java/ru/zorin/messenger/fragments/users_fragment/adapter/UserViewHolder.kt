package ru.zorin.messenger.fragments.users_fragment.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.zorin.messenger.R
import ru.zorin.messenger.fragments.users_fragment.UserListener
import ru.zorin.messenger.model.User

class UserViewHolder(var view: View, var listener: UserListener) : RecyclerView.ViewHolder(view) {

    private val userName = view.findViewById(R.id.name) as TextView
    private val avatarText = view.findViewById(R.id.avatar_text) as TextView
    private val avatarCircle = view.findViewById(R.id.avatar_circle) as ImageView
    private val rootMessage = view.findViewById(R.id.root_message) as CardView
    private val message = view.findViewById(R.id.message) as TextView


    fun bind(user: User){
        userName.text = user.login

        if (user.lastMessage.messege.isEmpty()){
            rootMessage.visibility = View.GONE
        }else{
            rootMessage.visibility = View.VISIBLE
            message.text = user.lastMessage.messege
        }

        avatarText.text = "${user.login[0].toUpperCase()}"
        when (user.login[0].toUpperCase().toString()){
            in "A".."E" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar1));
            in "F".."K" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar2));
            in "L".."N" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar3));
            in "O".."R" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar4));
            in "S".."X" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar5));
            in "Y".."Z" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar6));
            else -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.colorPrimary));
        }

        view.setOnClickListener {
            listener.onClickUser(user.login,user.userId)
        }
    }
}