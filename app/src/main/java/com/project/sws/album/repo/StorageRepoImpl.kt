package com.project.sws.album.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.project.sws.album.DataStoreConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StorageRepoImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    StorageRepo {
    override suspend fun storeBookmarkedAlbums(ids: Set<Int>) {
        dataStore.edit {
            val key = stringSetPreferencesKey(DataStoreConstants.KEY_BOOKMARKED_ALBUMS)
            it[key] = ids.map { it.toString() }.toSet()
        }
    }

    override val bookMarkedAlbums: Flow<Set<Int>>
        get() = dataStore.data.map {
            val key = stringSetPreferencesKey(DataStoreConstants.KEY_BOOKMARKED_ALBUMS)
            it[key]?.map { it.toInt() }?.toSet() ?: emptySet()
        }
}