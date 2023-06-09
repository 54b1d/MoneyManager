package com.sabid.moneymanager.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabid.moneymanager.MoneyManagerApp
import com.sabid.moneymanager.adapters.TransactionDetailedAdapter
import com.sabid.moneymanager.databinding.ActivityLedgerBinding
import com.sabid.moneymanager.viewModels.AccountViewModel
import com.sabid.moneymanager.viewModels.AccountViewModelFactory
import com.sabid.moneymanager.viewModels.TransactionViewModel
import com.sabid.moneymanager.viewModels.TransactionViewModelFactory

class LedgerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLedgerBinding
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as MoneyManagerApp).repository)
    }
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as MoneyManagerApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLedgerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Ledger"

        val adapter = TransactionDetailedAdapter()
        binding.rvLedger.adapter = adapter
        binding.rvLedger.setHasFixedSize(true)
        binding.rvLedger.layoutManager = LinearLayoutManager(this)

        // check for intent
        if (intent.hasExtra("accountId")) {
            val accountId = intent.getIntExtra("accountId", 0)
            // todo use observe view-model
            accountViewModel.getAccountWithBalanceOf(accountId).observe(this) {
                binding.tvAccount.text = it[0].name
                binding.tvAccountBalance.text = it[0].balance.toString()
            }

            transactionViewModel.getAllTransactionOf(accountId).observe(this) { transactionList ->
                transactionList.let { adapter.updateTransactionList(it) }
            }
        } else {
            Toast.makeText(this, "No Account ID Passed", Toast.LENGTH_SHORT).show()
        }
    }
}