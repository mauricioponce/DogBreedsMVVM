package com.example.doggiesmvvm.model

import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import org.junit.Test

import com.google.common.truth.Truth.assertThat

class MappersKtTest {

    @Test
    fun breedFromWrapper2Entity_empty() {
        //Given
        val wrapper = BreedWrapper("ok", mapOf())

        // The
        val result = breedFromWrapper2Entity(wrapper)

        // Then
        assertThat(result).isNotNull()
        assertThat(result).isEmpty()
    }

    @Test
    fun breedFromWrapper2Entity_null() {
        //Given
        val wrapper = null

        // The
        val result = breedFromWrapper2Entity(wrapper)

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun breedFromWrapper2Entity_happyCase() {
        //Given
        val wrapper = BreedWrapper("ok", mapOf("key1" to listOf("v1")))
        val expected = BreedEntity("key1")

        // The
        val result = breedFromWrapper2Entity(wrapper)

        // Then
        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
        assertThat(result).hasSize(1)
        assertThat(result).containsExactly(expected)

    }
}