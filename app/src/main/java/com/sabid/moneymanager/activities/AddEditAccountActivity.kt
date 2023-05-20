package com.sabid.moneymanager.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sabid.moneymanager.dataModels.Account
import com.sabid.moneymanager.MoneyManagerApp
import com.sabid.moneymanager.viewModels.AccountGroupViewModel
import com.sabid.moneymanager.viewModels.AccountGroupViewModelFactory
import com.sabid.moneymanager.viewModels.AccountViewModel
import com.sabid.moneymanager.viewModels.AccountViewModelFactory
import com.sabid.moneymanager.databinding.ActivityAddEditAccountBinding
import kotlinx.coroutines.Dispatchers


class AddEditAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddEditAccountBinding
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as MoneyManagerApp).repository)
    }
    private val accountGroupViewModel: AccountGroupViewModel by viewModels {
        AccountGroupViewModelFactory((application as MoneyManagerApp).repository)
    }
    private var accountId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Account Edit"
        if (intent.getIntExtra("accountId", 0) != 0) {
            accountId = intent.getIntExtra("accountId", 0)
            // hide add button
            binding.btnConfirmAddAccount.visibility = View.GONE
            // show update button
            binding.btnConfirmUpdateAccount.visibility = View.VISIBLE

            Log.d(javaClass.name, "Intent have Account ID")
            // get account info with name, groupId, opening balance

            accountViewModel.getAccountById(accountId)
                .observe(this) { account ->
                    // fill fields
                    binding.editName.setText(account.name)
                    binding.editOpeningBalance.setText(account.openingBalance.toString())
                    accountGroupViewModel.getAccountGroupById(account.accountGroupId)
                        .observe(this) { accountGroup ->
                            binding.editAccountGroup.setText(accountGroup.name)
                        }
                }
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
        binding.btnConfirmUpdateAccount.setOnClickListener {
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
                        accountViewModel.update(Account(accountId, name, openingBalance, groupId))
                        Toast.makeText(this, "Account Updated $name", Toast.LENGTH_SHORT).show()
                        // finish activity
                        finish()
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