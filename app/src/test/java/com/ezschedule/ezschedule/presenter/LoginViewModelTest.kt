package com.ezschedule.ezschedule.presenter

import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.fixture.data.LoginRequestFixture
import com.ezschedule.ezschedule.fixture.presentation.LoginPresentationFixture
import com.ezschedule.ezschedule.fixture.response.LoginResponseFixture
import com.ezschedule.ezschedule.presenter.viewModel.LoginViewModel
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
class LoginViewModelTest : CoroutineViewModelTest() {
    private lateinit var useCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    override fun setUp() {
        useCase = mockk(relaxed = true)
        viewModel = LoginViewModel(useCase)
    }

    @Test
    fun `WHEN successfully requested login SHOULD insert into the liveData of the tenant`() =
        runTest {
            coEvery {
                useCase.execute(LoginRequestFixture.getLoginRequestComplete().build())
            } returns ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete().build()
            )

            viewModel.login(LoginRequestFixture.getLoginRequestComplete().build())

            val result = viewModel.tenant.getOrAwaitValue()
            val expected = LoginPresentationFixture.getLoginPresentationComplete().build()

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN executing the request with an error SHOULD show error request`() =
        runTest {
            coEvery {
                useCase.execute(LoginRequestFixture.getLoginRequestComplete().build())
            } returns ResultWrapper.Error(Exception())

            viewModel.login(LoginRequestFixture.getLoginRequestComplete().build())

            val result = viewModel.error.getOrAwaitValue()
            val expected = Exception::class.java

            assertInstanceOf(expected, result)
        }
}