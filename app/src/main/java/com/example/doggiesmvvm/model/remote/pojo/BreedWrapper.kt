package com.example.doggiesmvvm.model.remote.pojo

data class BreedWrapper (val status: String, val message: Map<String, List<String>>)

data class BreedImagesWrapper(val status: String, val message: List<String>)