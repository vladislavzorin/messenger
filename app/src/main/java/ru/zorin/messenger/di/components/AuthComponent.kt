package ru.zorin.messenger.di.components

import dagger.Subcomponent
import ru.zorin.messenger.di.modules.AuthModule
import ru.zorin.messenger.di.scopes.AuthScope
import ru.zorin.messenger.fragments.auth_fragment.AuthFragment
import ru.zorin.messenger.fragments.registration_fragment.RegFragment

@Subcomponent(modules = [(AuthModule::class)])
@AuthScope
interface AuthComponent {

    fun inject(authFragment: AuthFragment)
    fun inject(regFragment: RegFragment)
}