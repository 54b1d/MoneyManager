package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("Delete from transactions")
    suspend fun deleteAll()

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT * FROM transactions")
    fun getAllTransaction(): Flow<List<Transaction>>
}