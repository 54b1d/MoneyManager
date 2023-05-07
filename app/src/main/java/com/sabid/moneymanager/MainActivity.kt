package com.sabid.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDatabase.getDatabase(this)

        fillDemoData()
    }

    private fun fillDemoData() {
        CoroutineScope(Dispatchers.IO).launch {
            db.accountGroupDao().insertAccountGroup(AccountGroup(1, "Income"))
            db.accountGroupDao().insertAccountGroup(AccountGroup(2, "Expense"))
            db.accountGroupDao().insertAccountGroup(AccountGroup(3, "Current"))
            // income accounts
            db.accountDao().insertAccount(Account(1, "Other", 0.0, 1))
            db.accountDao().insertAccount(Account(2, "Salary", 0.0, 1))
            db.accountDao().insertAccount(Account(3, "Bonus", 0.0, 1))
            // expense accounts
            db.accountDao().insertAccount(Account(4, "Social Life", 0.0, 2))
            db.accountDao().insertAccount(Account(5, "Mobile", 0.0, 2))
            db.accountDao().insertAccount(Account(6, "Other", 0.0, 2))
            // current accounts
            db.accountDao().insertAccount(Account(7, "Cash", 0.0, 3))
            db.accountDao().insertAccount(Account(8, "DBBL", 0.0, 3))

            // transaction types
            db.transactionTypeDao().insertTransactionType(TransactionType(1, "Income"))
            db.transactionTypeDao().insertTransactionType(TransactionType(2, "Expense"))
            db.transactionTypeDao().insertTransactionType(TransactionType(3, "Transfer"))

            // transactions
            db.transactionDao()
                .insertTransaction(Transaction(1, LocalDate.now().toString(), 1, 2, 8, 25000.0, ""))
            db.transactionDao().insertTransaction(
                Transaction(
                    0, LocalDate.now().toString(), 2, 8, 4, 2000.0, "Cox's bazaar"
                )
            )
            db.transactionDao().insertTransaction(
                Transaction(
                    0, LocalDate.now().toString(), 3, 8, 7, 10000.0, "withdraw from atm"
                )
            )
            db.transactionDao().insertTransaction(
                Transaction(
                    0, LocalDate.now().toString(), 2, 7, 5, 300.0, "recharge"
                )
            )
        }
    }
}