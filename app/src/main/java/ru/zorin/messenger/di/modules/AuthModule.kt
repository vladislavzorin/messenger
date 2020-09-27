package ru.zorin.messenger.di.modules

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import ru.zorin.messenger.di.scopes.AuthScope
import ru.zorin.messenger.repositories.AuthRepository

@Module
class AuthModule {

    @Provides
    @AuthScope
    internal fun provideAuthRepository(database : FirebaseDatabase,sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepository(database,sharedPreferences)
    }
}