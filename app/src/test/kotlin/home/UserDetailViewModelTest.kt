package home

import androidx.lifecycle.SavedStateHandle
import com.hometest.core.BaseTest
import com.hometest.domain.model.UserDetail
import com.hometest.domain.usecase.GetUserDetailUseCase
import com.hometest.userdetail.UserDetailViewModel
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserDetailViewModelTest : BaseTest() {

    private val getUserDetailUseCase: GetUserDetailUseCase = mock()
    private lateinit var userDetailViewModel: UserDetailViewModel
    private val savedStateHandle: SavedStateHandle = mock()

    @Before
    fun setUp() {
        whenever(savedStateHandle.get<String>("userName")).thenReturn("username")
        userDetailViewModel = UserDetailViewModel(getUserDetailUseCase, savedStateHandle)
    }

    @Test
    fun uiState_isLoadingWhenInitialized() = runUnconfinedTest {
        val uiState = userDetailViewModel.uiState.first()
        assertFalse(uiState.isLoading)
    }

    @Test
    fun uiState_updatesWithUserDetailOnSuccess() = runUnconfinedTest {
        val userDetail = UserDetail(1, "username", "name", "email", "location", 1, 1)
        whenever(getUserDetailUseCase.invoke("username")).thenReturn(Result.success(userDetail))

        userDetailViewModel = UserDetailViewModel(getUserDetailUseCase, savedStateHandle)
        val uiState = userDetailViewModel.uiState.first { !it.isLoading }

        assertEquals(userDetail, uiState.userDetail)
        assertTrue(uiState.errorMessage.isEmpty())
    }

    @Test
    fun uiState_updatesWithErrorMessageOnFailure() = runUnconfinedTest {
        val errorMessage = "User not found"
        whenever(getUserDetailUseCase.invoke("username")).thenReturn(
            Result.failure(
                Exception(
                    errorMessage
                )
            )
        )

        userDetailViewModel = UserDetailViewModel(getUserDetailUseCase, savedStateHandle)
        val uiState = userDetailViewModel.uiState.first { !it.isLoading }

        assertNull(uiState.userDetail)
        assertEquals(errorMessage, uiState.errorMessage)
    }
}