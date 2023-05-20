package com.sabid.moneymanager.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabid.moneymanager.MoneyManagerApp
import com.sabid.moneymanager.adapters.AccountWithBalanceAdapter
import com.sabid.moneymanager.databinding.ActivityAccountsBinding
import com.sabid.moneymanager.viewModels.AccountViewModel
import com.sabid.moneymanager.viewModels.AccountViewModelFactory

class AccountsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountsBinding
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as MoneyManagerApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AccountWithBalanceAdapter()
        binding.rvAccounts.adapter = adapter
        binding.rvAccounts.setHasFixedSize(true)
        binding.rvAccounts.layoutManager = LinearLayoutManager(this)

        accountViewModel.allAccountWithBalance.observe(this) {
            it.let { adapter.updateAccountWithBalanceList(it) }
        }

        binding.fabAddAccount.setOnClickListener {
            startActivity(Intent(this, AddEditAccountActivity::class.java))
        }
    }
}