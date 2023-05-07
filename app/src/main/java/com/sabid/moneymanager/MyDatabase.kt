package com.sabid.moneymanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AccountGroup::class, Account::class, TransactionType::class, Transaction::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun accountGroupDao(): AccountGroupDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionTypeDao(): TransactionTypeDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context, MyDatabase::class.java, "my_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}