package com.xiaoism.time.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xiaoism.time.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        TimeEntity::class,
        City::class,
        Person::class,
        Group::class,
        GroupPersonCrossRef::class],
    version = 2
)
public abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao
    abstract fun cityDao(): CityDao
    abstract val peopleDao: PersonDao
    abstract val groupDao: GroupDao

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
                    "synctime.db"
                ).createFromAsset("database/times.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class TimeDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {

                }
            }

        }
    }
}