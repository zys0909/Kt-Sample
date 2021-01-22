package com.dev.zhaoys.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.zhaoys.room.entity.UserEntity

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-26
 */
@Database(version = 1, entities = [UserEntity::class])
abstract class AppDataBase : RoomDatabase() {
    companion object {
        val instance: AppDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(App.instance, AppDataBase::class.java, "")
                .fallbackToDestructiveMigration().build()
        }
    }
}