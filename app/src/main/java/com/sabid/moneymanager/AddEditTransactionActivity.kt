package com.sabid.moneymanager

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.databinding.ActivityAddEditTransactionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditTransactionBinding
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as MoneyManagerApp).repository)
    }
    private val transactionTypeViewModel: TransactionTypeViewModel by viewModels {
        TransactionTypeViewModel.TransactionTypeViewModelFactory((application as MoneyManagerApp).repository)
    }
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as MoneyManagerApp).repository)
    }
    private var accountFromId: Int = 0
    private var accountToId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Transaction Edit"
        if (intent.getIntExtra("accountId", 0) != 0) {
            Log.d(javaClass.name, "Intent have Transaction ID")
        } else {
            Log.d(javaClass.name, "Intent new Transaction")
        }

        binding.editTransactionGroup.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // show bottomSheet fragment with list of account groups
                binding.editTransactionGroup.showDropDown()
            } else {
                // hide account groups fragments
                binding.editTransactionGroup.dismissDropDown()
            }
        }
        binding.editAccountFrom.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // show bottomSheet fragment with list of account groups
                binding.editAccountFrom.showDropDown()
            } else {
                // hide account groups fragments
                binding.editAccountFrom.dismissDropDown()
            }
        }
        binding.editAccountFrom.setOnItemClickListener { _, _, _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                accountFromId =
                    accountViewModel.getAccountByName(binding.editAccountFrom.text.toString()).id
            }
        }
        binding.editAccountTo.setOnItemClickListener { _, _, _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                accountToId =
                    accountViewModel.getAccountByName(binding.editAccountTo.text.toString()).id
            }
        }
        binding.editAccountTo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // show bottomSheet fragment with list of account groups
                binding.editAccountTo.showDropDown()
            } else {
                // hide account groups fragments
                binding.editAccountTo.dismissDropDown()
            }
        }

        binding.btnConfirmAddTransaction.setOnClickListener {
            addTransactionConfirmed()
        }

        transactionTypeViewModel.allTransactionType.observe(this) {
            binding.editTransactionGroup.setAdapter(
                ArrayAdapter(this, android.R.layout.select_dialog_item, it)
            )
        }

        accountViewModel.allAccount.observe(this) { accounts ->
            accounts?.let {
                val accountNameAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
                binding.editAccountFrom.setAdapter(accountNameAdapter)
                binding.editAccountTo.setAdapter(accountNameAdapter)
            }
        }
    }

    private fun addTransactionConfirmed() {
        // todo get date from date picker
        val trxDate = LocalDate.now().toString()
        var transactionTypeId = 0
        val transactionType = binding.editTransactionGroup.text.toString().trim()
        val accountFrom = binding.editAccountFrom.text.toString().trim()
        val accountTo = binding.editAccountTo.text.toString().trim()
        val amount = if (binding.editAmount.text.toString().trim()
                .toDoubleOrNull() == null
        ) 0.0 else binding.editAmount.text.toString().trim().toDouble()
        val narration = binding.editNarration.text.toString().trim()
        if (transactionType.isNotBlank() && accountFrom.isNotBlank() && accountTo.isNotBlank()
            && amount != 0.0 && accountFromId != 0 && accountToId != 0
        ) {
            try {
                when (transactionType) {
                    "Income" -> transactionTypeId = 1
                    "Expense" -> transactionTypeId = 2
                    "Transfer" -> transactionTypeId = 3
                }

                val transaction = Transaction(
                    0, trxDate, transactionTypeId, accountFromId, accountToId, amount, narration
                )
                Log.d("AddTransaction", "onCreate: $transaction")
                transactionViewModel.insert(transaction)
                Toast.makeText(this, "Transaction inserted", Toast.LENGTH_SHORT).show()

                // clear fields
                clearFields()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Caught Exception", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.editTransactionGroup.text.clear()
        binding.editAccountFrom.text.clear()
        binding.editAccountTo.text.clear()
        binding.editAmount.text.clear()

        accountFromId = 0
        accountToId = 0
    }
}