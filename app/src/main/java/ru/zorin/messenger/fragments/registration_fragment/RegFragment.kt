package ru.zorin.messenger.fragments.registration_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.auth_fragment.*
import kotlinx.android.synthetic.main.reg_fragment.*
import kotlinx.android.synthetic.main.reg_fragment.progress_bar
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.zorin.messenger.App
import ru.zorin.messenger.R
import ru.zorin.messenger.activity.MainActivity
import ru.zorin.messenger.repositories.AuthRepository
import javax.inject.Inject

class RegFragment :  MvpAppCompatFragment(),RegView {

    @InjectPresenter
    lateinit var presenter: RegPresenter

    lateinit var root: View

    @Inject
    lateinit var repository: AuthRepository

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
        root = inflater.inflate(R.layout.reg_fragment, container, false);
        return root
    }

    @ProvidePresenter
    fun provideAuthPresenter(): RegPresenter{
        return RegPresenter(repository)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.minusAuthComponent()
    }

    override fun onResume() {
        super.onResume()
        reg_btn.setOnClickListener{
            progress_bar.visibility = View.VISIBLE
            reg_btn.visibility = View.GONE

            if(email.text.isNotEmpty() && userName.text.isNotEmpty() && password1.text.isNotEmpty() && password2.text.isNotEmpty()){
                if(password1.text.toString() == password2.text.toString()){
                    presenter.singUp(userName.text.toString(),password1.text.toString(),email.text.toString())
                }else{
                    showMessage("Пароли не совпадают")
                    btnVisible()
                    password1.text.clear()
                    password2.text.clear()
                }
            }else{
                showMessage("Пустая строка")
                btnVisible()
            }
        }

        sign_up.setOnClickListener { (activity as MainActivity).showAuthFragment() }
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRegistrationError() {
        showMessage("Пользователь с таким логином уже существует")
        btnVisible()
    }

    override fun onRegistrationSuccess() {
        showMessage("Пользователь успешно зарегистрирован")
        btnVisible()
        email.text.clear()
        userName.text.clear()
        password1.text.clear()
        password2.text.clear()
        (activity as MainActivity).showAuthFragment()
    }

    fun btnVisible(){
        progress_bar.visibility = View.GONE
        reg_btn.visibility = View.VISIBLE
    }
}