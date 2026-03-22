package com.show.carhome.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CarBrandEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val page: Int = 0,
    val icon: String
)
