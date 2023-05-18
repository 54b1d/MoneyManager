package com.sabid.moneymanager.daos

import androidx.room.Dao
import androidx.room.Query
import com.sabid.moneymanager.dataModels.AccountWithBalance
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountWithBalanceDao {
    @Query(
        """SELECT acc.id, acc.name,
            IFNULL(acc.openingBalance
            -IFNULL((SELECT SUM(amount) FROM transactions WHERE account_from_id=acc.id),0)
            +IFNULL((SELECT SUM(amount) FROM transactions WHERE account_to_id=acc.id),0)
            ,0) AS balance FROM accounts acc;"""
    )
    fun allAccountWithBalance(): Flow<List<AccountWithBalance>>

    @Query(
        """SELECT acc.id, acc.name,
            IFNULL(acc.openingBalance
            -IFNULL((SELECT SUM(amount) FROM transactions WHERE account_from_id=acc.id),0)
            +IFNULL((SELECT SUM(amount) FROM transactions WHERE account_to_id=acc.id),0)
            ,0) AS balance FROM accounts acc where acc.id = :accountId;"""
    )
    fun getAccountWithBalance(accountId: Int): Flow<List<AccountWithBalance>>
}