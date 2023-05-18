package com.sabid.moneymanager.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.MoneyManagerApp
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
    }
}