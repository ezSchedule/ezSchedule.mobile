package com.ezschedule.ezschedule.presenter

import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.fixture.data.TenantLoginRequestFixture
import com.ezschedule.ezschedule.fixture.presentation.TenantPresentationFixture
import com.ezschedule.ezschedule.fixture.response.TenantLoginResponseFixture
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
                useCase.execute(TenantLoginRequestFixture.getTenantLoginRequestComplete().build())
            } returns ResultWrapper.Success(
                TenantLoginResponseFixture.getTenantLoginResponseComplete().build()
            )

            viewModel.login(TenantLoginRequestFixture.getTenantLoginRequestComplete().build())

            val result = viewModel.tenant.getOrAwaitValue()
            val expected = TenantPresentationFixture.getTenantPresentationComplete().build()

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN executing the request with an error SHOULD show error request`() =
        runTest {
            coEvery {
                useCase.execute(TenantLoginRequestFixture.getTenantLoginRequestComplete().build())
            } returns ResultWrapper.Error(Exception())

            viewModel.login(TenantLoginRequestFixture.getTenantLoginRequestComplete().build())

            val result = viewModel.error.getOrAwaitValue()
            val expected = Exception::class.java

            assertInstanceOf(expected, result)
        }
}