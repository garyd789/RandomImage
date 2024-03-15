package com.example.randomimage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    // Retrieve the image path from DataStore
    val imagePath: Flow<String> = dataStore.data.map { preferences ->
        preferences[IMAGE_PATH_KEY] ?: ""
    }

    // Retrieve the basic text string from DataStore
    val basicTextString: Flow<String> = dataStore.data.map { preferences ->
        preferences[BASIC_TEXT_KEY] ?: ""
    }

    companion object {
        private val IMAGE_PATH_KEY = stringPreferencesKey("image_path")
        private val BASIC_TEXT_KEY = stringPreferencesKey("basic_text")
        private var INSTANCE: PreferencesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile("settings")
                }
                INSTANCE = PreferencesRepository(dataStore)
            }
        }
        fun get(): PreferencesRepository {
            return INSTANCE ?: throw IllegalStateException(
                "PreferencesRepository must be initialized"
            )
        }
    }
    // Used to update image path
    suspend fun setImagePath(imagePath: String) {
        dataStore.edit { preferences ->
            preferences[IMAGE_PATH_KEY] = imagePath
        }
    }
    // Used to update the text
    suspend fun setBasicTextString(text: String) {
        dataStore.edit { preferences ->
            preferences[BASIC_TEXT_KEY] = text
        }
    }



}