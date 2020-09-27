package ru.zorin.messenger.fragments.registration_fragment

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.zorin.messenger.repositories.AuthRepository

@InjectViewState
class RegPresenter(var repository: AuthRepository) : MvpPresenter<RegView>(),RegListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.regListener = this
        repository.getMaxId()
    }

    fun singUp(login:String, password:String, email:String){
        repository.registerUser(login,password,email)
    }

    override fun onRegistrationError() {
       viewState.onRegistrationError()
    }

    override fun onRegistrationSuccess() {
        viewState.onRegistrationSuccess()
    }
}