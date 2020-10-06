package ru.zorin.messenger.activity

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.zorin.messenger.App
import ru.zorin.messenger.R
import ru.zorin.messenger.fragments.auth_fragment.AuthFragment
import ru.zorin.messenger.fragments.chat_fragment.ChatFragment
import ru.zorin.messenger.fragments.registration_fragment.RegFragment
import ru.zorin.messenger.fragments.users_fragment.UsersFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val fm = this.supportFragmentManager

    var isChatFragmentShow = false

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var mAuth : FirebaseAuth

    @Inject
    lateinit var authFragment:AuthFragment

    @Inject
    lateinit var regFragment:RegFragment

    @Inject
    lateinit var chatFragment:ChatFragment

    @Inject
    lateinit var usersFragment: UsersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        mAuth.signInAnonymously()
    }

    private fun initFragment(){
        if (sharedPreferences.getString("login","")!!.isEmpty()) {
            fm.beginTransaction()
                .apply {
                    add(R.id.fragmentLayout, authFragment)
                    addToBackStack(null)
                }
                .commit()
        }else{
            fm.beginTransaction()
                .apply {
                    add(R.id.fragmentLayout, usersFragment)
                    addToBackStack(null)
                }
                .commit()
            isChatFragmentShow = true
        }
    }

    fun showRegFragment(){
        fm.beginTransaction()
            .apply {
                replace(R.id.fragmentLayout, regFragment)
                addToBackStack(null)
            }
            .commit()
    }

    fun showAuthFragment(){
        fm.beginTransaction()
            .apply {
                replace(R.id.fragmentLayout, authFragment)
                addToBackStack(null)
            }
            .commit()
    }

    fun showUsersFragment(){
        fm.beginTransaction()
            .apply {
                replace(R.id.fragmentLayout, usersFragment)
                addToBackStack(null)
            }
            .commit()
        isChatFragmentShow = true
    }


    fun showChatFragment(dialogId:Int,friendLogin:String){
        chatFragment.dialogId = dialogId
        chatFragment.friendLogin = friendLogin
        fm.beginTransaction()
            .apply {
                replace(R.id.fragmentLayout, chatFragment)
                addToBackStack(null)
            }
            .commit()
        isChatFragmentShow = false
    }

    override fun onBackPressed() {
        if(isChatFragmentShow){
            finish()
        }else{
            super.onBackPressed()
        }
    }

    fun setSystemBarColorInt(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = color
        }
    }

}