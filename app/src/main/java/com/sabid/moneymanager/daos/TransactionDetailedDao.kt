package com.sabid.moneymanager.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sabid.moneymanager.dataModels.TransactionDetailed
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDetailedDao {
    @Query(
        """SELECT trx.id, trx.trxDate, trx.transaction_type_id as transactionTypeId,
            trxType.name as transactionTypeName, trx.account_from_id as accountFromId,
            accFrom.name as accountFromName, trx.account_to_id as accountToId,
            accTo.name as accountToName, trx.amount, trx.narration FROM transactions trx
        inner join transaction_types trxType on trxType.id=trx.transaction_type_id
        inner join accounts accFrom on accFrom.id=trx.account_from_id
        inner join accounts accTo on accTo.id=trx.account_to_id
        order by trx.trxDate desc, trx.id desc;"""
    )
    fun allTransactionDetailed(): Flow<List<TransactionDetailed>>

    @Query(
        """SELECT trx.id, trx.trxDate, trx.transaction_type_id as transactionTypeId,
            trxType.name as transactionTypeName, trx.account_from_id as accountFromId,
            accFrom.name as accountFromName, trx.account_to_id as accountToId,
            accTo.name as accountToName, trx.amount, trx.narration FROM transactions trx
        inner join transaction_types trxType on trxType.id=trx.transaction_type_id
        inner join accounts accFrom on accFrom.id=trx.account_from_id
        inner join accounts accTo on accTo.id=trx.account_to_id
        where trx.account_from_id = :accountId OR trx.account_to_id = :accountId
        order by trx.trxDate desc, trx.id desc;"""
    )
    fun allTransactionDetailed(accountId: Int): Flow<List<TransactionDetailed>>

    @Query(
        """SELECT trx.id, trx.trxDate, trx.transaction_type_id as transactionTypeId,
            trxType.name as transactionTypeName, trx.account_from_id as accountFromId,
            accFrom.name as accountFromName, trx.account_to_id as accountToId,
            accTo.name as accountToName, trx.amount, trx.narration FROM transactions trx
        inner join transaction_types trxType on trxType.id=trx.transaction_type_id
        inner join accounts accFrom on accFrom.id=trx.account_from_id
        inner join accounts accTo on accTo.id=trx.account_to_id
        where trx.id = :transactionId LIMIT 1;"""
    )
    fun getDetailedTransactionById(transactionId: Int): LiveData<TransactionDetailed>
}