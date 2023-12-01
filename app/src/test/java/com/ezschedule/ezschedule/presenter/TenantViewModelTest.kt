package com.ezschedule.ezschedule.presenter

import com.ezschedule.network.domain.useCase.tenant.LoginUseCase
import com.ezschedule.ezschedule.fixture.data.TenantRequestFixture
import com.ezschedule.ezschedule.fixture.presentation.TenantPresentationFixture
import com.ezschedule.ezschedule.fixture.response.TenantResponseFixture
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.ezschedule.util.CoroutineViewModelTest
import com.ezschedule.ezschedule.util.getOrAwaitValue
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertInstanceOf


@OptIn(ExperimentalCoroutinesApi::class)
class TenantViewModelTest : CoroutineViewModelTest() {
    private lateinit var useCase: LoginUseCase
    private lateinit var viewModel: TenantViewModel

    @Before
    override fun setUp() {
        useCase = mockk(relaxed = true)
        viewModel = TenantViewModel(useCase)
    }

    @Test
    fun `WHEN successfully requested login SHOULD insert into the liveData of the tenant`() =
        runTest {
            coEvery {
                useCase.execute(TenantRequestFixture.getTenantRequestComplete().build())
            } returns ResultWrapper.Success(
                TenantResponseFixture.getTenantResponseComplete().build()
            )

            viewModel.login(TenantRequestFixture.getTenantRequestComplete().build())

            val result = viewModel.loginSuccess.getOrAwaitValue()
            val expected = TenantPresentationFixture.getTenantPresentationComplete().build()

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN executing the request with an error SHOULD show error request`() =
        runTest {
            coEvery {
                useCase.execute(TenantRequestFixture.getTenantRequestComplete().build())
            } returns ResultWrapper.Error(Exception())

            viewModel.login(TenantRequestFixture.getTenantRequestComplete().build())

            val result = viewModel.error.getOrAwaitValue()
            val expected = Exception::class.java

            assertInstanceOf(expected, result)
        }
}