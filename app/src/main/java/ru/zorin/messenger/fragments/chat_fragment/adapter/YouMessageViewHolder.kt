package ru.zorin.messenger.fragments.chat_fragment.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.zorin.messenger.R
import ru.zorin.messenger.model.Message

class YouMessageViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    private val text = view.findViewById(R.id.text_content) as TextView
    private val autor = view.findViewById(R.id.autor_name) as TextView
    private val time = view.findViewById(R.id.time_content) as TextView
    private val avatarText = view.findViewById(R.id.avatar_text) as TextView
    private val avatarCircle = view.findViewById(R.id.avatar_circle) as ImageView

    @SuppressLint("ResourceAsColor")
    fun bind(message: Message, position: Int){
        text.text = "${message.messege}"
        autor.text = "${message.username}"
        time.text = "${message.time}"
        avatarText.text = "${message.username[0].toUpperCase()}"

        when (message.username[0].toUpperCase().toString()){
            in "A".."E" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar1));
            in "F".."K" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar2));
            in "L".."N" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar3));
            in "O".."R" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar4));
            in "S".."X" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar5));
            in "Y".."Z" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar6));
            else -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.colorPrimary));
        }

    }

}