package com.sabid.moneymanager.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_groups")
data class AccountGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
