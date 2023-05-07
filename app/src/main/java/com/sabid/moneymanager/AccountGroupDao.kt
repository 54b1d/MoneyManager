package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccountGroup(accountGroup: AccountGroup): Long

    @Query("SELECT * FROM account_groups WHERE id=:id")
    suspend fun getAccountGroupById(id: Int): AccountGroup?
}