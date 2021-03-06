package ru.zorin.messenger.di.modules

import dagger.Module
import dagger.Provides
import ru.zorin.messenger.fragments.auth_fragment.AuthFragment
import ru.zorin.messenger.fragments.chat_fragment.ChatFragment
import ru.zorin.messenger.fragments.registration_fragment.RegFragment
import ru.zorin.messenger.fragments.users_fragment.UsersFragment
import javax.inject.Singleton

@Module
class FragmentModule {

    @Provides
    @Singleton
    fun provideAuthFragment(): AuthFragment {
        return AuthFragment()
    }

    @Provides
    fun provideChatFragment(): ChatFragment {
        return ChatFragment()
    }

    @Provides
    @Singleton
    fun provideRegFragment(): RegFragment {
        return RegFragment()
    }

    @Provides
    @Singleton
    fun provideUsersFragment(): UsersFragment {
        return UsersFragment()
    }
}