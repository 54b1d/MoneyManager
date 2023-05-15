package com.sabid.moneymanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

class TransactionTypeViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val allTransactionType: LiveData<List<TransactionType>> =
        dataRepository.allTransactionType.asLiveData()

    class TransactionTypeViewModelFactory(private val repository: DataRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionTypeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TransactionTypeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}