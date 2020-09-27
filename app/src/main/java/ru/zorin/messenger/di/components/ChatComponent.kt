package ru.zorin.messenger.di.components

import dagger.Subcomponent
import ru.zorin.messenger.di.modules.ChatModule
import ru.zorin.messenger.di.scopes.ChatScope
import ru.zorin.messenger.fragments.chat_fragment.ChatFragment

@Subcomponent(modules = [(ChatModule::class)])
@ChatScope
interface ChatComponent {

    fun inject(chatFragment:ChatFragment)
}