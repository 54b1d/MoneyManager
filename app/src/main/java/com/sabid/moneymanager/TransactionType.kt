package com.sabid.moneymanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_types")
data class TransactionType(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
