package com.sabid.moneymanager.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sabid.moneymanager.dataModels.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    @Query("Delete from transactions")
    suspend fun deleteAll()

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT * FROM transactions")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Query("DELETE FROM transactions WHERE id = :transactionID")
    suspend fun delete(transactionID: Int)
}