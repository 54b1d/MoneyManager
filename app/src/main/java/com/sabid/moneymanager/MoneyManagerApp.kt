package com.sabid.moneymanager

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MoneyManagerApp : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { MyDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        DataRepository(
            database.transactionTypeDao(),
            database.transactionDao(),
            database.accountGroupDao(),
            database.accountDao(),
            database.transactionDetailedDao(),
            database.accountWithBalanceDao()
        )
    }
}
