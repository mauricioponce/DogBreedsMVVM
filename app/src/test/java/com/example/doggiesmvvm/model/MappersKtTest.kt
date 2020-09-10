package com.example.doggiesmvvm.model

import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import org.junit.Test

import com.google.common.truth.Truth.assertThat

class MappersKtTest {

    @Test
    fun breedFromWrapper2Entity() {
        //Given
        val l = listOf<String>()
        val breedWrapper = BreedWrapper("ok", mapOf("1" to l, "2" to l))
        val expected = listOf(BreedEntity("1"), BreedEntity("2"))

        // When
        val result = breedFromWrapper2Entity(breedWrapper)

        // Then
        assertThat(result).isNotNull()
        assertThat(result).hasSize(2)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun breedFromWrapper2Entity_nullBreeds() {
        //Given
        val breedWrapper = null

        // When
        val result = breedFromWrapper2Entity(breedWrapper)

        // Then
        assertThat(result).isNull()
    }
}