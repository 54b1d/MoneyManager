package com.sabid.moneymanager.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        childColumns = ["account_from_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.RESTRICT
    ), ForeignKey(
        entity = Account::class,
        childColumns = ["account_to_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.RESTRICT
    ), ForeignKey(
        entity = TransactionType::class,
        childColumns = ["transaction_type_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.RESTRICT
    )]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val trxDate: String,
    @ColumnInfo(name = "transaction_type_id") val transactionTypeId: Int,
    @ColumnInfo(name = "account_from_id") val accountFromId: Int,
    @ColumnInfo(name = "account_to_id") val accountToId: Int,
    val amount: Double,
    val narration: String
)
