package com.sabid.moneymanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.sabid.moneymanager.dataModels.AccountGroup
import com.sabid.moneymanager.DataRepository

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
