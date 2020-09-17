package com.dev.zhaoys.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dev.zhaoys.room.entity.UserEntity

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