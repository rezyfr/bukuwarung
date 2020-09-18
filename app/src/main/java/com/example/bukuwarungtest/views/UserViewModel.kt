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
import timber.log.Timber

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _stateFetchUser = MutableLiveData<StateFetchUser>()
    val stateFetchUser: LiveData<StateFetchUser> get() = _stateFetchUser
    private val _stateUpdateUser = MutableLiveData<StateUpdateUser>()
    val stateUpdateUser: LiveData<StateUpdateUser> get() = _stateUpdateUser

    suspend fun fetchUser() {
        viewModelScope.launch {
            runCatching {
                _stateFetchUser.postValue(
                    StateFetchUser.Loading(
                        true
                    )
                )
                userRepository.fetchUsers()
            }.onSuccess {
                withContext(Dispatchers.IO) {
                    it.body()?.data?.let { it1 -> userRepository.saveUsers(it1) }
                }
                _stateFetchUser.postValue(
                    StateFetchUser.Success(
                        true
                    )
                )
            }.onFailure {
                _stateFetchUser.postValue(StateFetchUser.Error("Gagal loading data"))
            }
        }
    }

    suspend fun getUserList(): List<User> {
        return userRepository.getUserList()
    }

    suspend fun updateUser(email: String, name: String, id: Int) {
        val string = name.split(" ").toTypedArray()
        if (string.size == 2) {
            val user = User(first_name = string[0], last_name = string[1], email = email)
            withContext(Dispatchers.IO) {
                userRepository.updateUser(user).also {
                    val updatedUser = userRepository.getUserById(id)
                    Timber.d("UpdatedUser: $updatedUser")
                    _stateUpdateUser.postValue(
                        StateUpdateUser.Success(
                            updatedUser
                        )
                    )
                }
            }
        } else if(string.size == 1){
            val user = User(first_name = name, last_name = " ", email = email)
            withContext(Dispatchers.IO) {
                userRepository.updateUser(user).also {
                    val updatedUser = userRepository.getUserById(id)
                    Timber.d("UpdatedUser: $updatedUser")
                    _stateUpdateUser.postValue(
                        StateUpdateUser.Success(
                            updatedUser
                        )
                    )
                }
            }
        } else {
            _stateUpdateUser.postValue(
                StateUpdateUser.Error(
                    "Input valid first and last name"
                )
            )
        }
    }

    sealed class StateFetchUser {
        data class Loading(val loading: Boolean) : StateFetchUser()
        data class Success(val success: Boolean) : StateFetchUser()
        data class Error(val message: String) : StateFetchUser()
    }

    sealed class StateUpdateUser {
        data class Success(val user: User) : StateUpdateUser()
        data class Error(val message: String) : StateUpdateUser()
    }
}

