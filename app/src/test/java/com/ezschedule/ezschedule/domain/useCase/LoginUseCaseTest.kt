package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.ezschedule.fixture.data.TenantRequestFixture
import com.ezschedule.ezschedule.fixture.response.TenantResponseFixture
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
    private lateinit var repository: TenantRepository
    private lateinit var useCase: LoginUseCase

    @Before
    override fun setUp() {
        repository = mockk(relaxed = true)
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `WHEN successfully requested login SHOULD return a ResultWrapper successfully`() = runTest {
        val expected = ResultWrapper.Success(
            TenantResponseFixture.getTenantResponseComplete().build()
        )
        coEvery { useCase.execute(any()) } returns expected

        val result =
            useCase.execute(TenantRequestFixture.getTenantRequestComplete().build())

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN When requested login fails SHOULD return an error ResultWrapper`() = runTest {
        val expected = ResultWrapper.Error(Exception())
        coEvery { useCase.execute(any()) } returns expected

        val result =
            useCase.execute(TenantRequestFixture.getTenantRequestComplete().build())

        assertEquals(expected, result)
    }
}