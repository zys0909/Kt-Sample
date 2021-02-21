package com.group.dev.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.group.dev.room.entity.UserEntity

/**
 * 描述:
 *
 * author zys
 * create by 2020/9/17
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

}