package com.example.topmovies.repository


import com.example.topmovies.api.ApiServices
import com.example.topmovies.db.MovieEntity
import com.example.topmovies.db.MoviesDao
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices, private val dao: MoviesDao) {
    //Api
    suspend fun detailMovie(id: Int) = api.detailMovie(id)

    //Database
    suspend fun insertMovie(entity: MovieEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MovieEntity) = dao.deleteMovie(entity)
    suspend fun existsMovie(id: Int) = dao.existsMovie(id)
}