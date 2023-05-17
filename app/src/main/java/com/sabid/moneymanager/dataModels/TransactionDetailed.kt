package com.sabid.moneymanager.dataModels

data class TransactionDetailed(
    val id: Int,
    val trxDate: String,
    val transactionTypeId: Int,
    val transactionTypeName: String,
    val accountFromId: Int,
    val accountFromName: String,
    val accountToId: Int,
    val accountToName: String,
    val amount: Double,
    val narration: String
)
