package com.sabid.moneymanager

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.databinding.ActivityAddEditAccountBinding


class AddEditAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddEditAccountBinding
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as MoneyManagerApp).repository)
    }
    private val accountGroupViewModel: AccountGroupViewModel by viewModels {
        AccountGroupViewModelFactory((application as MoneyManagerApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Account Edit"
        if (intent.getIntExtra("accountId", 0) != 0) {
            Log.d(javaClass.name, "Intent have Account ID")
        } else {
            Log.d(javaClass.name, "Intent new Account")
        }

        binding.editAccountGroup.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // show bottomSheet fragment with list of account groups
                binding.editAccountGroup.showDropDown()
            } else {
                // hide account groups fragments
                binding.editAccountGroup.dismissDropDown()
            }
        }
        binding.btnConfirmAddAccount.setOnClickListener {
            var groupId = 0
            val name = binding.editName.text.toString().trim()
            val groupName = binding.editAccountGroup.text.toString().trim()
            val openingBalance = if (binding.editOpeningBalance.text.toString().trim()
                    .toDoubleOrNull() == null
            ) 0.0 else binding.editOpeningBalance.text.toString().trim().toDouble()
            if (name.isNotBlank() && groupName.isNotBlank()) {
                when (groupName) {
                    "Income" -> groupId = 1
                    "Expense" -> groupId = 2
                    "Current" -> groupId = 3
                }
                if (groupId != 0) {
                    try {
                        // pass to repository
                        accountViewModel.insert(Account(0, name, openingBalance, groupId))
                        Toast.makeText(this, "Added new Account $name", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        accountGroupViewModel.allAccountGroup.observe(this) {
            binding.editAccountGroup.setAdapter(
                ArrayAdapter(this, android.R.layout.select_dialog_item, it)
            )
        }
    }
}