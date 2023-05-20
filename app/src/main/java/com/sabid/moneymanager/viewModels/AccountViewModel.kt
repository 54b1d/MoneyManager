package com.sabid.moneymanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sabid.moneymanager.DataRepository
import com.sabid.moneymanager.dataModels.Account
import com.sabid.moneymanager.dataModels.AccountWithBalance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val allAccount: LiveData<List<Account>> = dataRepository.allAccount.asLiveData()
    val allAccountWithBalance: LiveData<List<AccountWithBalance>> =
        dataRepository.allAccountWithBalance.asLiveData()

    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.insertAccount(account)
    }

    fun getAccountByName(name: String): LiveData<Account> = viewModelScope.let {
        return dataRepository.getAccountByName(name)
    }

    fun getAccountById(accountId: Int): LiveData<Account> = viewModelScope.let {
        return dataRepository.getAccountById(accountId)
    }

    fun getAccountWithBalanceOf(accountId: Int): LiveData<List<AccountWithBalance>> {
        return dataRepository.getAccountWithBalance(accountId).asLiveData()
    }

    fun update(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.updateAccount(account)
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