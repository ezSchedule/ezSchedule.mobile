package com.ezschedule.ezschedule.data.repository

import com.ezschedule.ezschedule.fixture.data.LoginRequestFixture
import com.ezschedule.ezschedule.util.CoroutineViewModelTest
import com.ezschedule.network.data.api.LoginEndpoint
import com.ezschedule.network.data.network.exception.ClientException
import com.ezschedule.network.data.network.exception.ServerException
import com.ezschedule.network.data.network.exception.UnknownResponseException
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRepositoryTest : CoroutineViewModelTest() {
    private lateinit var endpoint: LoginEndpoint
    private lateinit var repository: LoginRepository

    @Before
    override fun setUp() {
        endpoint = mockk(relaxed = true)
        repository = LoginRepository(endpoint)
    }

    @Test
    fun `WHEN raise exception with status code 400 of POST, SHOULD convert to response`() =
        runTest {
            val message = "HTTP 400 Bad Request"
            val code = 400
            setupExceptionMockPost(code, message)

            val result =
                repository.login(LoginRequestFixture.getLoginRequestComplete().build())
            val error = (result as ResultWrapper.Error).error as ClientException

            coVerify { endpoint.singUp(any()) }
            assertEquals(error.code, code)
            assertEquals(error.message, message)
        }

    @Test
    fun `WHEN raise exception with status code 500 of POST, SHOULD convert to response`() =
        runTest {
            val message = "HTTP 500 Internal Server Error"
            val code = 500
            setupExceptionMockPost(code, message)

            val result =
                repository.login(LoginRequestFixture.getLoginRequestComplete().build())
            val error = (result as ResultWrapper.Error).error as ServerException

            coVerify { endpoint.singUp(any()) }
            assertEquals(error.code, code)
            assertEquals(error.message, message)
        }

    @Test
    fun `WHEN raise exception with status code 600 of POST, SHOULD convert to response`() =
        runTest {
            val message = "HTTP 600 Unknown Exception"
            val code = 600
            setupExceptionMockPost(code, message)

            val result =
                repository.login(LoginRequestFixture.getLoginRequestComplete().build())
            val error = (result as ResultWrapper.Error).error as UnknownResponseException

            coVerify { endpoint.singUp(any()) }
            assertEquals(error.code, code)
            assertEquals(error.message, message)
        }

    private fun setupExceptionMockPost(statusCode: Int, messageError: String) {
        val exception = mockk<HttpException> {
            every { code() } returns statusCode
            every { message } returns messageError
        }

        coEvery { endpoint.singUp(any()) } throws exception
    }

//    @Test
//    fun `WHEN raise exception with status code 400, SHOULD convert to response`() =
//        runTest {
//            val message = "HTTP 400 Bad Request"
//            setupExceptionMockGet(statusCode = 400, messageError = message)
//
//            val result = repository.getUser()
//            val error = (result as ResultWrapper.Error).error as ClientException
//
//            coVerify { endpoint.getUser() }
//            assertEquals(error.code, 400)
//            assertEquals(error.message, message)
//        }
//
//    @Test
//    fun `WHEN raise exception with status code 500, SHOULD convert to response`() =
//        runTest {
//            val message = "HTTP 500 Internal Server Error"
//            setupExceptionMockGet(statusCode = 500, messageError = message)
//
//            val result = repository.getUser()
//            val error = (result as ResultWrapper.Error).error as ServerException
//
//            coVerify { endpoint.getUser() }
//            assertEquals(error.code, 500)
//            assertEquals(error.message, message)
//        }
//
//    private fun setupExceptionMockGet(statusCode: Int, messageError: String) {
//        val exception = mockk<HttpException> {
//            every { code() } returns statusCode
//            every { message } returns messageError
//        }
//
//        coEvery { endpoint.getUser() } throws exception
//    }
}