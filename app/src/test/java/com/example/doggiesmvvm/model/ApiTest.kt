package com.example.doggiesmvvm.model

import com.example.doggiesmvvm.model.remote.DoggiesAPI
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://stackoverflow.com/questions/39827081/unit-testing-android-application-with-retrofit-and-rxjava
// https://android.jlelse.eu/unit-test-api-calls-with-mockwebserver-d4fab11de847
class ApiTest {

    var mMockWebServer: MockWebServer? = null

    lateinit var api : DoggiesAPI

    @Before
    fun setup() {
        mMockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mMockWebServer!!.url("/"))
            .build()

        api = retrofit.create(DoggiesAPI::class.java)
    }

    @Test
    fun listaRazas_happyCase() = runBlocking {
        // Given
        val mResultList = BreedWrapper("ok", mapOf("k1" to listOf<String>(), "k2" to listOf()))
        mMockWebServer!!.enqueue(MockResponse().setBody(Gson().toJson(mResultList)))

        // When
        val result = api.listaRazas()

        // Then
        assertThat(result).isNotNull()
        assertThat(result.isSuccessful).isTrue()

        val message = result.body()?.message
        assertThat(message).isNotNull()
        assertThat(message).hasSize(2)
        assertThat(message?.contains("k1")).isTrue()
        assertThat(message?.contains("k2")).isTrue()
    }

    @Test
    fun listaRazas_happyUrl() = runBlocking {
        // Given
        val mResultList = BreedWrapper("ok", mapOf("k1" to listOf<String>(), "k2" to listOf()))
        mMockWebServer!!.enqueue(MockResponse().setBody(Gson().toJson(mResultList)))

        // When
        val result = api.listaRazas()

        // Then
        val request = mMockWebServer!!.takeRequest()
        assertThat(request.path).isEqualTo("/breeds/list/all")
    }
}