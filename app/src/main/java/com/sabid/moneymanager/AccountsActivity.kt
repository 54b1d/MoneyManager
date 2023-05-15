package com.sabid.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.databinding.ActivityAccountsBinding

class AccountsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}