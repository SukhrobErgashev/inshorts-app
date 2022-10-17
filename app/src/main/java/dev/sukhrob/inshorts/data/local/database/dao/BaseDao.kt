package dev.sukhrob.inshorts.data.local.database.dao

import androidx.room.*

interface BaseDao<T> {

    // used to insert list of T type objects
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<T>)

    // used to insert T type object
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T)

    // used to update T type object
    @Update
    suspend fun update(item: T)

}