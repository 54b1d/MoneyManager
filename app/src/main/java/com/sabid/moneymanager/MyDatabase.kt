package com.sabid.moneymanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sabid.moneymanager.daos.AccountDao
import com.sabid.moneymanager.daos.AccountGroupDao
import com.sabid.moneymanager.daos.AccountWithBalanceDao
import com.sabid.moneymanager.daos.TransactionDao
import com.sabid.moneymanager.daos.TransactionDetailedDao
import com.sabid.moneymanager.daos.TransactionTypeDao
import com.sabid.moneymanager.dataModels.Account
import com.sabid.moneymanager.dataModels.AccountGroup
import com.sabid.moneymanager.dataModels.Transaction
import com.sabid.moneymanager.dataModels.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [AccountGroup::class, Account::class, TransactionType::class, Transaction::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun accountGroupDao(): AccountGroupDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionTypeDao(): TransactionTypeDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionDetailedDao(): TransactionDetailedDao
    abstract fun accountWithBalanceDao(): AccountWithBalanceDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context, MyDatabase::class.java, "my_database"
                    )
                        .addCallback(MyDatabaseCallBack(scope))
                        .build()
                }
            }
            return INSTANCE!!
        }
    }


    private class MyDatabaseCallBack(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.accountGroupDao(), database.transactionTypeDao())
                }
            }
        }

        suspend fun populateDatabase(
            accountGroupDao: AccountGroupDao,
            transactionTypeDao: TransactionTypeDao
        ) {
            accountGroupDao.insertAccountGroup(AccountGroup(1, "Income"))
            accountGroupDao.insertAccountGroup(AccountGroup(2, "Expense"))
            accountGroupDao.insertAccountGroup(AccountGroup(3, "Current"))
            // transaction types
            transactionTypeDao.insertTransactionType(TransactionType(1, "Income"))
            transactionTypeDao.insertTransactionType(TransactionType(2, "Expense"))
            transactionTypeDao.insertTransactionType(TransactionType(3, "Transfer"))
        }
    }

}