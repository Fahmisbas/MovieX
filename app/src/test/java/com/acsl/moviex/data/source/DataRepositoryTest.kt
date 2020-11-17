package com.acsl.moviex.data.source

import com.acsl.moviex.data.DataDummy
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.data.source.remote.response.RemoteDataSource
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class DataRepositoryTest {
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = DataRepository(remote)

    private val moviesResponse = DataDummy.generateRemoteDummyMovies()
    private val resultId = moviesResponse[0].results[0].id


    @Test
    fun getAllMovies() {
        `when`<List<MovieResponse>>(remote.getAllMovies()).thenReturn(moviesResponse)
        val courseEntities = academyRepository.getAllCourses()
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(moviesResponse.size.toLong(), courseEntities.size.toLong())
    }

}