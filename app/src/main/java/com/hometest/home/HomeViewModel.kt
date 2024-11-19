package com.hometest.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hometest.data.MAX_PAGE_SIZE
import com.hometest.domain.model.UserInfo
import com.hometest.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    val users: Flow<PagingData<UserInfo>> =
        getUsersUseCase(GetUsersUseCase.Params(0, MAX_PAGE_SIZE)).cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun onLoadStateUpdate(loadState: CombinedLoadStates) {
        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update { it.copy(errorMessage = error.orEmpty()) }
    }

    fun onErrorMessageEventConsumed() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}

data class HomeState(
    val errorMessage: String = ""
)