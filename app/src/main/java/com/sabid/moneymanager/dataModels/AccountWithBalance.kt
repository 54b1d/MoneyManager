package com.sabid.moneymanager.dataModels

data class AccountWithBalance(
    val id: Int,
    val name: String,
    val groupId: Int,
    val groupName: String,
    val balance: Double
)
