package ru.zorin.messenger.fragments.users_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.zorin.messenger.App
import ru.zorin.messenger.R
import ru.zorin.messenger.activity.MainActivity
import ru.zorin.messenger.fragments.users_fragment.adapter.UserAdapter
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.model.User
import ru.zorin.messenger.repositories.ChatRepository
import java.util.ArrayList
import javax.inject.Inject

class UsersFragment :  MvpAppCompatFragment(),UsersView {

    @InjectPresenter
    lateinit var presenter:UsersPresenter

    @Inject
    lateinit var repository: ChatRepository

    lateinit var root: View
    lateinit var mRecyclerView: RecyclerView
    lateinit var toolbar:Toolbar

    var adapter:UserAdapter = UserAdapter()

    var isShowProgressbar = true

    @ProvidePresenter
    fun provideChatPresenter():UsersPresenter{
        return UsersPresenter(repository)
    }

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
        root = inflater.inflate(R.layout.users_fragment, container, false);
        toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_chat)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        App.minusChatComponent()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        presenter.loadUsers()
        presenter.setSubTitle()
    }

    private fun initRecyclerView(){
        mRecyclerView = root.findViewById(R.id.usersRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = adapter
        adapter.listener = presenter
        adapter.listItems.clear()
    }

    override fun showUsers(list: List<User>) {
        adapter.setData(list.toMutableList())
    }

    override fun openChat(dialogId: Int,friendLogin:String) {
        (activity as MainActivity).showChatFragment(dialogId,friendLogin)
    }

    override fun showProgressBar() {
        if (isShowProgressbar) {
            progress_bar.visibility = View.VISIBLE
        }
    }

    override fun closeProgressBar() {
        progress_bar.visibility  = View.GONE
        isShowProgressbar = false
    }

    override fun setSubTitle(title: String) {
        toolbar.subtitle = title
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