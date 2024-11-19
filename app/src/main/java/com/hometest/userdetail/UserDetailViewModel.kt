package com.hometest.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometest.domain.model.UserDetail
import com.hometest.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailState())
    val uiState = _uiState.asStateFlow()

    private val userName: String? = savedStateHandle["userName"]

    init {
        if (userName?.isNotEmpty() == true) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val result = getUserDetailUseCase(userName)
                if (result.isSuccess) {
                    _uiState.update { it.copy(userDetail = result.getOrNull()) }
                } else {
                    _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.message.orEmpty()) }
                }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onErrorMessageEventConsumed() {
        _uiState.update { it.copy(errorMessage = "") }
    }

    fun onLoadingEventConsumed() {
        _uiState.update { it.copy(isLoading = false) }
    }
}

data class UserDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val userDetail: UserDetail? = null
)