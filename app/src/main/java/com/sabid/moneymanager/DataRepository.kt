package com.sabid.moneymanager

import androidx.annotation.WorkerThread
import com.sabid.moneymanager.daos.AccountDao
import com.sabid.moneymanager.daos.AccountGroupDao
import com.sabid.moneymanager.daos.TransactionDao
import com.sabid.moneymanager.daos.TransactionDetailedDao
import com.sabid.moneymanager.daos.TransactionTypeDao
import com.sabid.moneymanager.dataModels.Account
import com.sabid.moneymanager.dataModels.AccountGroup
import com.sabid.moneymanager.dataModels.Transaction
import com.sabid.moneymanager.dataModels.TransactionDetailed
import com.sabid.moneymanager.dataModels.TransactionType
import kotlinx.coroutines.flow.Flow

class DataRepository(
    private val transactionTypeDao: TransactionTypeDao,
    private val transactionDao: TransactionDao,
    private val accountGroupDao: AccountGroupDao,
    private val accountDao: AccountDao,
    private val transactionDetailedDao: TransactionDetailedDao
) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransaction()
    val allTransactionType: Flow<List<TransactionType>> = transactionTypeDao.getAllTransactionType()
    val allAccountGroup: Flow<List<AccountGroup>> = accountGroupDao.getAllAccountGroup()
    val allAccount: Flow<List<Account>> = accountDao.getAllAccount()
    val allDetailedTransaction: Flow<List<TransactionDetailed>> =
        transactionDetailedDao.allTransactionDetailed()

    fun getAllTransactionOf(accountId: Int): Flow<List<TransactionDetailed>> {
        return transactionDetailedDao.allTransactionDetailed(accountId)
    }

    @WorkerThread
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    @WorkerThread
    suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)
    }

    @WorkerThread
    fun getAccountByName(name: String): Account {
        return accountDao.getAccountByName(name)
    }

    @WorkerThread
    fun getAllAccountName(): List<Account> {
        return accountDao.getALlAccountName()
    }
}