package com.sabid.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.databinding.ActivityLedgerBinding

class LedgerActivity : AppCompatActivity() {
    lateinit var binding: ActivityLedgerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLedgerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}