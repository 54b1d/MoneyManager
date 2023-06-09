package com.sabid.moneymanager.daos

import androidx.room.Dao
import androidx.room.Query
import com.sabid.moneymanager.dataModels.AccountWithBalance
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountWithBalanceDao {
    @Query(
        """SELECT acc.id, acc.name, acc.account_group_id as groupId,
            (select name from account_groups where account_groups.id=acc.account_group_id) as groupName,
            IFNULL(acc.openingBalance
            -IFNULL((SELECT SUM(amount) FROM transactions WHERE account_from_id=acc.id),0)
            +IFNULL((SELECT SUM(amount) FROM transactions WHERE account_to_id=acc.id),0)
            ,0) AS balance FROM accounts acc ORDER BY acc.name ASC;"""
    )
    fun allAccountWithBalance(): Flow<List<AccountWithBalance>>

    @Query(
        """SELECT acc.id, acc.name, acc.account_group_id as groupId,
            (select name from account_groups where account_groups.id=acc.account_group_id) as groupName,
            IFNULL(acc.openingBalance
            -IFNULL((SELECT SUM(amount) FROM transactions WHERE account_from_id=acc.id),0)
            +IFNULL((SELECT SUM(amount) FROM transactions WHERE account_to_id=acc.id),0)
            ,0) AS balance FROM accounts acc where acc.id = :accountId;"""
    )
    fun getAccountWithBalance(accountId: Int): Flow<List<AccountWithBalance>>
}