package com.sabid.moneymanager.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_types")
data class TransactionType(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
