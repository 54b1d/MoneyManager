package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account): Long

    @Query("SELECT * FROM accounts WHERE id=:id")
    suspend fun getAccountById(id: Int): Account?
}