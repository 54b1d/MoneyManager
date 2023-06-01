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
import kotlin.math.abs

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
            it.let {
                // todo remove filter after implementing grouped viewpager
                adapter.updateAccountWithBalanceList(it.filter { it1 -> it1.groupId == 3 })
                // liability = sum of current account balance in negative
                var liability = 0.0
                // asset = sum of current account balance in positive
                var asset = 0.0
                // net = asset - liability + income - expense
                val net: Double

                var income = 0.0
                var expense = 0.0

                for (ac in it) {
                    when (ac.groupId) {
                        1 -> income += ac.balance
                        2 -> expense += ac.balance
                        3 -> if (ac.balance < 0.0) liability += ac.balance else asset += ac.balance
                    }
                }

                net = abs(asset) - abs(liability) + abs(income) - abs(expense)

                binding.tvAssetLiabilityNet.text = "Asset: $asset, Liability: $liability, Net: $net"
                binding.tvIncomeExpense.text = "Income: $income, Expense: $expense"
            }
        }

        binding.fabAddAccount.setOnClickListener {
            startActivity(Intent(this, AddEditAccountActivity::class.java))
        }
    }
}