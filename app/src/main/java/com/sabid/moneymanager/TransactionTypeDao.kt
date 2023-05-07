package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactionType(transactionType: TransactionType): Long

    @Query("SELECT * FROM transaction_types WHERE id=:id")
    suspend fun getTransactionTypeById(id: Int): TransactionType?
}