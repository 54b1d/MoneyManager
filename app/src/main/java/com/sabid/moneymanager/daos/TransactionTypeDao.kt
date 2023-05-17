package com.sabid.moneymanager.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sabid.moneymanager.dataModels.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactionType(transactionType: TransactionType): Long

    @Query("SELECT * FROM transaction_types WHERE id=:id")
    suspend fun getTransactionTypeById(id: Int): TransactionType?

    @Query("SELECT * FROM transaction_types")
    fun getAllTransactionType(): Flow<List<TransactionType>>
}