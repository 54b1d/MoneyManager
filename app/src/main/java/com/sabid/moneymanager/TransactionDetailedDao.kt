package com.sabid.moneymanager

import androidx.room.Dao
import androidx.room.Query
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
}