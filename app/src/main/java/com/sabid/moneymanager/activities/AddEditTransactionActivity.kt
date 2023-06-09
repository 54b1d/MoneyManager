package com.sabid.moneymanager.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sabid.moneymanager.MoneyManagerApp
import com.sabid.moneymanager.R
import com.sabid.moneymanager.dataModels.Transaction
import com.sabid.moneymanager.databinding.ActivityAddEditTransactionBinding
import com.sabid.moneymanager.viewModels.AccountViewModel
import com.sabid.moneymanager.viewModels.AccountViewModelFactory
import com.sabid.moneymanager.viewModels.TransactionTypeViewModel
import com.sabid.moneymanager.viewModels.TransactionViewModel
import com.sabid.moneymanager.viewModels.TransactionViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    private lateinit var datePickerDateFormat: DateTimeFormatter
    private val dbDateFormat = DateTimeFormatter.ISO_DATE
    private var transactionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        datePickerDateFormat =
            DateTimeFormatter.ofPattern(resources.getString(R.string.date_format))
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Transaction Edit"

        val today = LocalDate.now()
        binding.textDate.text = today.format(dbDateFormat)

        if (intent.getIntExtra("transactionId", 0) != 0) {
            transactionId = intent.getIntExtra("transactionId", 0)
            // hide add button
            binding.btnConfirmAddTransaction.visibility = View.GONE
            // show update button
            binding.btnConfirmUpdateTransaction.visibility = View.VISIBLE
            binding.btnConfirmDeleteTransaction.visibility = View.VISIBLE

            // fill data
            transactionViewModel.getDetailedTransactionById(transactionId).observe(this) {
                try {

                    binding.textDate.text = it.trxDate
                    binding.editTransactionGroup.setText(it.transactionTypeName)
                    binding.editAccountFrom.setText(it.accountFromName)
                    binding.editAccountTo.setText(it.accountToName)
                    binding.editAmount.setText(it.amount.toString())
                    binding.editNarration.setText(it.narration)
                    accountFromId = it.accountFromId
                    accountToId = it.accountToId
                } catch (ignored: Exception) {
                    Log.e(javaClass.name, "Exception Ignored")
                }
            }
        } else {
            Log.d(javaClass.name, "Intent new Transaction")
        }

        binding.textDate.setOnClickListener {
            val mDialog = DatePickerDialog(
                this, R.style.DialogTheme, { _: DatePicker?, year1: Int, month1: Int, day1: Int ->
                    val lt = LocalDate.parse("$year1-${month1 + 1}-$day1", datePickerDateFormat)
                    binding.textDate.text = lt.toString()
                }, today.year, today.monthValue - 1, today.dayOfMonth
            )
            mDialog.show()
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
            accountViewModel.getAccountByName(binding.editAccountFrom.text.toString())
                .observe(this) {
                    accountFromId = it.id
                }

        }

        binding.editAccountTo.setOnItemClickListener { _, _, _, _ ->
            accountViewModel.getAccountByName(binding.editAccountTo.text.toString())
                .observe(this) {
                    accountToId = it.id
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
        binding.btnConfirmUpdateTransaction.setOnClickListener {
            updateTransactionConfirmed()
        }
        binding.btnConfirmDeleteTransaction.setOnClickListener {
            finish()
            transactionViewModel.deleteTransaction(transactionId)

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
                binding.editAccountFrom.threshold = 1
                binding.editAccountTo.setAdapter(accountNameAdapter)
                binding.editAccountTo.threshold = 1
            }
        }
    }

    private fun addTransactionConfirmed() {
        val trxDate = LocalDate.parse(binding.textDate.text.toString(), dbDateFormat).toString()
        Log.d(javaClass.name, "addTransactionConfirmed: $trxDate")
        var transactionTypeId = 0
        val transactionType = binding.editTransactionGroup.text.toString().trim()
        val accountFrom = binding.editAccountFrom.text.toString().trim()
        val accountTo = binding.editAccountTo.text.toString().trim()
        val amount = if (binding.editAmount.text.toString().trim()
                .toDoubleOrNull() == null
        ) 0.0 else binding.editAmount.text.toString().trim().toDouble()
        val narration = binding.editNarration.text.toString().trim()
        if (transactionType.isNotBlank() && accountFrom.isNotBlank() && accountTo.isNotBlank() && amount != 0.0 && accountFromId != 0 && accountToId != 0) {
            try {
                when (transactionType) {
                    "Income" -> transactionTypeId = 1
                    "Expense" -> transactionTypeId = 2
                    "Transfer" -> transactionTypeId = 3
                }

                val transaction = Transaction(
                    0, trxDate, transactionTypeId, accountFromId, accountToId, amount, narration
                )
                transactionViewModel.insert(transaction)
                Toast.makeText(this, "Transaction inserted", Toast.LENGTH_SHORT).show()

                // finish activity
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Caught Exception", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTransactionConfirmed() {
        val trxDate = LocalDate.parse(binding.textDate.text.toString(), dbDateFormat).toString()
        var transactionTypeId = 0
        val transactionType = binding.editTransactionGroup.text.toString().trim()
        val accountFrom = binding.editAccountFrom.text.toString().trim()
        val accountTo = binding.editAccountTo.text.toString().trim()
        val amount = if (binding.editAmount.text.toString().trim()
                .toDoubleOrNull() == null
        ) 0.0 else binding.editAmount.text.toString().trim().toDouble()
        val narration = binding.editNarration.text.toString().trim()

        if (transactionType.isNotBlank() && accountFrom.isNotBlank() && accountTo.isNotBlank() && amount != 0.0 && accountFromId != 0 && accountToId != 0) {
            try {
                when (transactionType) {
                    "Income" -> transactionTypeId = 1
                    "Expense" -> transactionTypeId = 2
                    "Transfer" -> transactionTypeId = 3
                }

                val transaction = Transaction(
                    transactionId,
                    trxDate,
                    transactionTypeId,
                    accountFromId,
                    accountToId,
                    amount,
                    narration
                )
                transactionViewModel.update(transaction)
                Toast.makeText(this, "Transaction Updated", Toast.LENGTH_SHORT).show()
                // finish activity
                finish()
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