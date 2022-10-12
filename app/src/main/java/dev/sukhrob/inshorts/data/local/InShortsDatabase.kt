package dev.sukhrob.inshorts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.sukhrob.inshorts.data.local.dao.InShortsDao
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article

@Database(entities = [ArticleEntity::class], version = 1)
abstract class InShortsDatabase : RoomDatabase() {

    abstract fun getInShortsDao(): InShortsDao

    companion object {
        private var INSTANCE: InShortsDatabase? = null

        fun init(context: Context): InShortsDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            val instance =
                Room.databaseBuilder(
                    context,
                    InShortsDatabase::class.java,
                    "inshorts.db"
                ).build()

            INSTANCE = instance
            return instance
        }
    }

}