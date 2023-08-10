package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.LoginRepository
import com.ezschedule.ezschedule.fixture.data.LoginRequestFixture
import com.ezschedule.ezschedule.fixture.response.LoginResponseFixture
import com.ezschedule.ezschedule.util.CoroutineViewModelTest
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest : CoroutineViewModelTest() {
    private lateinit var repository: LoginRepository
    private lateinit var useCase: LoginUseCase

    @Before
    override fun setUp() {
        repository = mockk(relaxed = true)
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `WHEN successfully requested login SHOULD return a ResultWrapper successfully`() = runTest {
        val expected = ResultWrapper.Success(
            LoginResponseFixture.getLoginResponseComplete().build()
        )
        coEvery { useCase.execute(any()) } returns expected

        val result =
            useCase.execute(LoginRequestFixture.getLoginRequestComplete().build())

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN When requested login fails SHOULD return an error ResultWrapper`() = runTest {
        val expected = ResultWrapper.Error(Exception())
        coEvery { useCase.execute(any()) } returns expected

        val result =
            useCase.execute(LoginRequestFixture.getLoginRequestComplete().build())

        assertEquals(expected, result)
    }
}