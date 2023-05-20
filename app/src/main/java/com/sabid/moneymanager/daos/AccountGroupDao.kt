package com.sabid.moneymanager.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sabid.moneymanager.dataModels.AccountGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccountGroup(accountGroup: AccountGroup): Long

    @Query("SELECT * FROM account_groups WHERE id=:id LIMIT 1")
    fun getAccountGroupById(id: Int): LiveData<AccountGroup>

    @Query("SELECT * FROM account_groups")
    fun getAllAccountGroup(): Flow<List<AccountGroup>>
}