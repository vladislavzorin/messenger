package ru.zorin.messenger.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext():Context{
        return this.context
    }

    @Provides
    @Singleton
    internal fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    internal fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context : Context): SharedPreferences {
        return context.getSharedPreferences("USER_INFO_IN_MESSENGER", Context.MODE_PRIVATE);
    }

}