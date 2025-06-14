package com.example.movieexplorer.common.data

interface ApiMapper<Domain, Entity>{
    fun mapToDomain(apiDto: Entity): Domain
}