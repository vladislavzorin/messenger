package ru.zorin.messenger

import android.app.Application
import android.content.Context
import android.util.Log
import ru.zorin.messenger.di.components.AppComponent
import ru.zorin.messenger.di.components.AuthComponent
import ru.zorin.messenger.di.components.ChatComponent
import ru.zorin.messenger.di.components.DaggerAppComponent
import ru.zorin.messenger.di.modules.AppModule
import ru.zorin.messenger.di.modules.AuthModule
import ru.zorin.messenger.di.modules.ChatModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger(this.applicationContext)
    }

    companion object {

        lateinit var appComponent: AppComponent
        private var authComponent: AuthComponent? = null
        private var chatComponent: ChatComponent? = null

        fun initDagger(context: Context){
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .build()
        }

        fun plusAuthComponent(){
            if (authComponent == null){
                authComponent = appComponent.plusAuthComponent(AuthModule())
            }
        }

        fun minusAuthComponent(){
            authComponent = null
        }

        fun plusChatComponent(){
            if (chatComponent == null){
                chatComponent = appComponent.plusChatComponent(ChatModule())
            }
        }

        fun minusChatComponent(){
            chatComponent = null
        }


        fun getAuthComponent():AuthComponent = authComponent!!

        fun getChatComponent():ChatComponent = chatComponent!!

    }

}