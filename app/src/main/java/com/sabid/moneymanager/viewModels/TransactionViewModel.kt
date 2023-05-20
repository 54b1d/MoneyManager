package com.sabid.moneymanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sabid.moneymanager.DataRepository
import com.sabid.moneymanager.dataModels.Transaction
import com.sabid.moneymanager.dataModels.TransactionDetailed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val allTransactions: LiveData<List<Transaction>> = dataRepository.allTransactions.asLiveData()
    val allTransactionDetailed: LiveData<List<TransactionDetailed>> =
        dataRepository.allDetailedTransaction.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.insertTransaction(transaction)
    }

    fun update(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.updateTransaction(transaction)
    }

    fun getAllTransactionOf(accountId: Int): LiveData<List<TransactionDetailed>> {
        return dataRepository.getAllTransactionOf(accountId).asLiveData()
    }

    fun getDetailedTransactionById(transactionID: Int): LiveData<TransactionDetailed> {
        return dataRepository.getDetailedTransactionById(transactionID)
    }

    fun deleteTransaction(transactionID: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.deleteTransaction(transactionID)
    }
}

class TransactionViewModelFactory(private val repository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
