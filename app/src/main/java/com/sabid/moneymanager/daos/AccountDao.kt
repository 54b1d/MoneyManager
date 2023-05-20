package com.sabid.moneymanager.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sabid.moneymanager.dataModels.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAccount(account: Account)

    @Query("SELECT * FROM accounts WHERE id=:id LIMIT 1")
    fun getAccountById(id: Int): LiveData<Account>

    @Query("SELECT * FROM accounts WHERE name=:name")
    fun getAccountByName(name: String): Account

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getALlAccountName(): List<Account>

    @Query("SELECT * FROM accounts")
    fun getAllAccount(): Flow<List<Account>>
}