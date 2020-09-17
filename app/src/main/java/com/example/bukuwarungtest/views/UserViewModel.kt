package com.example.bukuwarungtest.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _stateUser = MutableLiveData<StateFetchUser>()
    val stateFetchUser: LiveData<StateFetchUser> get() = _stateUser

    suspend fun fetchUser() {
        viewModelScope.launch {
            runCatching {
                _stateUser.postValue(
                    StateFetchUser.Loading(
                        true
                    )
                )
                userRepository.fetchUsers()
            }.onSuccess {
                withContext(Dispatchers.IO){
                    it.body()?.data?.let { it1 -> userRepository.saveUsers(it1) }
                }
                _stateUser.postValue(
                    StateFetchUser.Success(
                        true
                    )
                )
            }.onFailure {
                _stateUser.postValue(StateFetchUser.Error("Gagal loading data"))
            }
        }
    }

    suspend fun getUserList(): List<User> {
        return userRepository.getUserList()
    }

    sealed class StateFetchUser {
        data class Loading(val loading: Boolean) : StateFetchUser()
        data class Success(val success: Boolean) : StateFetchUser()
        data class Error(val message: String) : StateFetchUser()
    }
}

