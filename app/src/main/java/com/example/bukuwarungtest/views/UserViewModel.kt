package com.example.bukuwarungtest.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuwarungtest.data.model.ProfileModel
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.data.repositories.UserRepository
import com.example.bukuwarungtest.utils.PrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(
    private val userRepository: UserRepository,
    private val prefManager: PrefManager
) : ViewModel() {
    private val _stateFetchUser = MutableLiveData<StateFetchUser>()
    val stateFetchUser: LiveData<StateFetchUser> get() = _stateFetchUser

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

    fun getProfileDetail(): ProfileModel {
        return ProfileModel(
            name = prefManager.UserManager().getCurrentName(),
            email = prefManager.UserManager().getCurrentEmail(),
            phone = prefManager.UserManager().getCurrentPhone(),
            img_path = prefManager.UserManager().getCurrentPicPath()
        )
    }

    fun updateProfile(profileModel: ProfileModel): ProfileModel{
        prefManager.getUserManager().saveCurrentName(profileModel.name)
        prefManager.getUserManager().saveCurrentUserEmail(profileModel.email)
        prefManager.getUserManager().saveCurrentUserPhone(profileModel.phone)
        prefManager.getUserManager().saveCurrentPicPath(profileModel.img_path)
        return ProfileModel(
            name = prefManager.UserManager().getCurrentName(),
            email = prefManager.UserManager().getCurrentEmail(),
            phone = prefManager.UserManager().getCurrentPhone(),
            img_path = prefManager.UserManager().getCurrentPicPath()
        )
    }

    sealed class StateFetchUser {
        data class Loading(val loading: Boolean) : StateFetchUser()
        data class Success(val success: Boolean) : StateFetchUser()
        data class Error(val message: String) : StateFetchUser()
    }
}

