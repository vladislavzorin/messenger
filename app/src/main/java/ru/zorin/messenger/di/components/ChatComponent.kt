package ru.zorin.messenger.di.components

import dagger.Subcomponent
import ru.zorin.messenger.di.modules.ChatModule
import ru.zorin.messenger.di.scopes.ChatScope
import ru.zorin.messenger.fragments.chat_fragment.ChatFragment
import ru.zorin.messenger.fragments.users_fragment.UsersFragment

@Subcomponent(modules = [(ChatModule::class)])
@ChatScope
interface ChatComponent {

    fun inject(chatFragment:ChatFragment)
    fun inject(usersFragment: UsersFragment)
}