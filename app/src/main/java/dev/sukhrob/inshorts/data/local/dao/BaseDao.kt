package dev.sukhrob.inshorts.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import dev.sukhrob.inshorts.domain.model.Article

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)

}