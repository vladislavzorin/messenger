package ru.zorin.messenger.di.modules

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import ru.zorin.messenger.di.scopes.ChatScope
import ru.zorin.messenger.repositories.ChatRepository

@Module
class ChatModule {

    @Provides
    @ChatScope
    internal fun provideChatRepository(database : FirebaseDatabase, sharedPreferences: SharedPreferences,mAuth : FirebaseAuth): ChatRepository {
        return ChatRepository(database,sharedPreferences,mAuth)
    }

}