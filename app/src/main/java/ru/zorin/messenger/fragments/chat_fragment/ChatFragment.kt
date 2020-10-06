package ru.zorin.messenger.fragments.chat_fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.zorin.messenger.App
import ru.zorin.messenger.R
import ru.zorin.messenger.activity.MainActivity
import ru.zorin.messenger.fragments.chat_fragment.adapter.MessageAdapter
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.repositories.ChatRepository
import java.util.ArrayList
import javax.inject.Inject


class ChatFragment :  MvpAppCompatFragment(),ChatView {

    @InjectPresenter
    lateinit var presenter:ChatPresenter

    @Inject
    lateinit var repository:ChatRepository

    @Inject
    lateinit var mAuth : FirebaseAuth

    lateinit var root: View
    lateinit var mRecyclerView: RecyclerView
    lateinit var toolbar:Toolbar

    var adapter:MessageAdapter = MessageAdapter()

    var dialogId:Int = 0

    var friendLogin:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        App.plusChatComponent()
        App.getChatComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setSystemBarColorInt(Color.parseColor("#006ACF"))
        root = inflater.inflate(R.layout.chat_fragment, container, false);
        toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_chat)
        setSubTitle(friendLogin)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        return root
    }

    @ProvidePresenter
    fun provideChatPresenter():ChatPresenter{
        return ChatPresenter(repository)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.minusChatComponent()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        presenter.getMessage(dialogId)

        btn_send.setOnClickListener {
            if (edit_text_content.text.isNotEmpty()) {
                presenter.sendMessage(edit_text_content.text.toString())
                edit_text_content.setText("")
            }
        }
    }

    fun initRecyclerView(){
        mRecyclerView = root.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = adapter
        adapter.userId = repository.getUserId()

        mRecyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom){
                mRecyclerView.post { scrollToLastPosition() }
            }
        };
    }

    override fun showChat(list: List<Message>) {
        adapter.setData(list as MutableList<Message>)
    }

    override fun scrollToLastPosition() {
        mRecyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    override fun showProgressBar() {
        showChat(ArrayList<Message>())
        progress_bar.visibility  = View.VISIBLE
    }

    override fun closeProgressBar() {
        progress_bar.visibility  = View.GONE
    }

    override fun setSubTitle(str: String) {
        val avatarCircle = root.findViewById(R.id.avatar_circle) as ImageView
        val avatarText = root.findViewById(R.id.avatar_text) as TextView
        val toolbarName = root.findViewById(R.id.toolbar_name) as TextView
        val backButton = root.findViewById(R.id.back_btn) as ImageButton

        avatarText.text = str[0].toUpperCase().toString()
        toolbarName.text = str

        when (str[0].toUpperCase().toString()){
            in "A".."E" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar1));
            in "F".."K" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar2));
            in "L".."N" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar3));
            in "O".."R" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar4));
            in "S".."X" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar5));
            in "Y".."Z" -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.avatar6));
            else -> avatarCircle.setColorFilter(ContextCompat.getColor( avatarCircle.context,R.color.colorPrimary));
        }

        backButton.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chat, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> {
                presenter.logOut()
                (activity as MainActivity).showAuthFragment()
            }

        }
        return true
    }

}