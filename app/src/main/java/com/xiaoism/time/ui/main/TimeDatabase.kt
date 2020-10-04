package com.xiaoism.time.ui.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TimeEntity::class], version = 1, exportSchema = false)
public abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao

    companion object {
        @Volatile
        private var INSTANCE: TimeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TimeDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeDatabase::class.java,
                    "time_database"
                ).addCallback(TimeDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class TimeDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database -> scope.launch {
                populateInitialDatabase(database.timeDao())
            } }

        }

        suspend fun populateInitialDatabase(timeDao: TimeDao) {
            timeDao.deleteAll()

            var time1 = TimeEntity("time 1")
            timeDao.insert(time1)
            var time2 = TimeEntity("time 2")
            timeDao.insert(time2)
        }
    }
}