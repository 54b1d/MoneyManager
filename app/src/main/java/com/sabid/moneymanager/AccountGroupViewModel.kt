package com.sabid.moneymanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

class AccountGroupViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val allAccountGroup: LiveData<List<AccountGroup>> =
        dataRepository.allAccountGroup.asLiveData()
}

class AccountGroupViewModelFactory(private val repository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountGroupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountGroupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
