package com.example.doggiesmvvm.model

import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper

fun breedFromWrapper2Entity(wrapper: BreedWrapper?): List<BreedEntity>? {
    return wrapper?.message?.keys?.map { BreedEntity(it) }
}