package com.example.aidar_hw_6_1.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aidar_hw_6_1.data.AppDatabase
import com.example.aidar_hw_6_1.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserProfileState(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val image: String? = null
)

class ProfileScreenViewModel(private val db: AppDatabase) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> get() = _state

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> get() = _toastMessage

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                db.profileDao().getAll().firstOrNull()?.let { profile ->
                    _state.update {
                        it.copy(
                            id = profile.id,
                            name = profile.name,
                            image = profile.image,
                            email = profile.email,
                            password = profile.password
                        )
                    }
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error loading profile: ${e.message}"
            }
        }
    }

    fun setName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun setEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun setProfileImageUri(uri: String?) {
        _state.update { it.copy(image = uri) }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            try {
                with(state.value) {
                    db.profileDao().save(
                        Profile(
                            id = id,
                            name = name,
                            email = email,
                            image = image,
                            password = password
                        )
                    )
                }
                _toastMessage.value = "Profile saved"
            } catch (e: Exception) {
                _toastMessage.value = "Error saving: ${e.message}"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}