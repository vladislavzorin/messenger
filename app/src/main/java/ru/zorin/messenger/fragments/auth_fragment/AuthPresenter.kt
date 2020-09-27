package ru.zorin.messenger.fragments.auth_fragment

import android.util.Log
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.zorin.messenger.repositories.AuthRepository

@InjectViewState
class AuthPresenter(var repository: AuthRepository) : MvpPresenter<AuthView>(), AuthListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.listener = this
    }

    fun singIn(login:String, password:String){
        repository.singIn(login,password)
    }

    override fun onLoginSuccess(login: String, id: Int) {
        repository.saveUserInfo(login,id)
        viewState.onLoginSuccess()
    }

    override fun onLoginError() {
        Log.d("mLog","ERROR")
        viewState.onLoginError()
    }
}