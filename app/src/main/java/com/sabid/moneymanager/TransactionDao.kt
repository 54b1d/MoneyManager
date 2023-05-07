package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getTransactionById(id: Int): Transaction?
}