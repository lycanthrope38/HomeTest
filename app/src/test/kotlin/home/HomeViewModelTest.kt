package home

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.hometest.core.BaseTest
import com.hometest.domain.model.UserInfo
import com.hometest.domain.usecase.GetUsersUseCase
import com.hometest.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class HomeViewModelTest : BaseTest() {

    private val getUsersUseCase: GetUsersUseCase = mock()
    private lateinit var homeViewModel: HomeViewModel

    private val users = listOf(UserInfo(1, "username", "avatarUrl", "htmlUrl"))

    private val pagingData: Flow<PagingData<UserInfo>> = flowOf(PagingData.from(users))

    @Before
    fun setUp() {
        whenever(getUsersUseCase.invoke(any())).thenReturn(pagingData)
        homeViewModel = HomeViewModel(getUsersUseCase)
    }

    @Test
    fun onLoadStateUpdate_updatesUiStateWithError() = runUnconfinedTest {
        val errorMessage = "Network error"
        val loadState = mockLoadState(
            refresh = LoadState.Error(Exception(errorMessage)),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
        )

        homeViewModel.onLoadStateUpdate(loadState)

        val uiState = homeViewModel.uiState.value
        assertEquals(errorMessage, uiState.errorMessage)
    }

    @Test
    fun onLoadStateUpdate_updatesUiStateWithSuccess() = runUnconfinedTest {
        val loadState = mockLoadState(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
        )

        homeViewModel.onLoadStateUpdate(loadState)

        val uiState = homeViewModel.uiState.value
        assertTrue(uiState.errorMessage.isEmpty())
    }

    private fun mockLoadState(
        refresh: LoadState,
        prepend: LoadState,
        append: LoadState
    ): CombinedLoadStates =
        CombinedLoadStates(
            refresh = refresh,
            prepend = prepend,
            append = append,
            source = LoadStates(refresh, prepend, append)
        )
}