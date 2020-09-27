package ru.zorin.messenger.fragments.chat_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        App.plusChatComponent()
        App.getChatComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
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
        presenter.getMessage()

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
        progress_bar.visibility  = View.VISIBLE
    }

    override fun closeProgressBar() {
        progress_bar.visibility  = View.GONE
    }

    override fun setSubTitle(str: String) {
        toolbar.subtitle = str
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