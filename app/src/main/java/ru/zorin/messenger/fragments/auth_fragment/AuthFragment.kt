package ru.zorin.messenger.fragments.auth_fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.auth_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.zorin.messenger.App
import ru.zorin.messenger.R
import ru.zorin.messenger.activity.MainActivity
import ru.zorin.messenger.repositories.AuthRepository
import javax.inject.Inject

class AuthFragment : MvpAppCompatFragment(), AuthView {

    @Inject
    lateinit var appContext:Context

    @Inject
    lateinit var repository:AuthRepository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @InjectPresenter
    lateinit var authPresenter: AuthPresenter

    private lateinit var root:View;

    override fun onCreate(savedInstanceState: Bundle?) {
        App.plusAuthComponent()
        App.getAuthComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setSystemBarColorInt(Color.parseColor("#E600838F"))
        root = inflater.inflate(R.layout.auth_fragment, container, false);
        return root
    }

    @ProvidePresenter
    fun provideAuthPresenter():AuthPresenter{
        return AuthPresenter(repository)
    }

    override fun onResume() {
        super.onResume()

        fab.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            fab.alpha = 0f

            if (login_edit.text!!.isNotEmpty() && pass_edit.text!!.isNotEmpty()){
                authPresenter.singIn(login_edit.text.toString(),pass_edit.text.toString())
            }else{
                showMessage("Пустое поле")
                progress_bar.visibility = View.GONE
                fab.alpha = 1f
            }
        }

        registration.setOnClickListener {
            (activity as MainActivity).showRegFragment()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        App.minusAuthComponent()
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess() {
        progress_bar.visibility = View.GONE
        fab.alpha = 1f

        (activity as MainActivity).showChatFragment()
    }

    override fun onLoginError() {
        progress_bar.visibility = View.GONE
        fab.alpha = 1f
        Snackbar.make(root, "Ошибка авторизации!", Snackbar.LENGTH_SHORT).show()
    }

}
