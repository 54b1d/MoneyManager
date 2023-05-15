package com.sabid.moneymanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val allAccount: LiveData<List<Account>> = dataRepository.allAccount.asLiveData()

    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.insertAccount(account)
    }

    fun getAccountByName(name: String): Account = viewModelScope.let {
        return dataRepository.getAccountByName(name)
    }
}

class AccountViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return AccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}