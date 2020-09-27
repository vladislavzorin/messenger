package ru.zorin.messenger.di.components

import dagger.Component
import ru.zorin.messenger.activity.MainActivity
import ru.zorin.messenger.di.modules.AppModule
import ru.zorin.messenger.di.modules.AuthModule
import ru.zorin.messenger.di.modules.ChatModule
import ru.zorin.messenger.di.modules.FragmentModule
import ru.zorin.messenger.fragments.auth_fragment.AuthFragment
import ru.zorin.messenger.fragments.registration_fragment.RegFragment
import javax.inject.Singleton

@Component(modules = [(AppModule::class),(FragmentModule::class)])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun plusAuthComponent(authModule: AuthModule):AuthComponent
    fun plusChatComponent(chatModule: ChatModule):ChatComponent

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule:AppModule): Builder
    }
}