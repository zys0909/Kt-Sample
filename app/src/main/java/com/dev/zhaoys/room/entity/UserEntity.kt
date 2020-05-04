package com.dev.zhaoys.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-26
 */
@Entity(tableName = "user")
class UserEntity(@PrimaryKey val id: String, val name: String)