package com.sabid.moneymanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_groups")
data class AccountGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
