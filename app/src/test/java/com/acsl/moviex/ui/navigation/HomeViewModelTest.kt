package com.acsl.moviex.ui.navigation

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun getMovieData() {
        val movies = viewModel.getMovieData()
        assertNotNull(movies)
        assertEquals(11, movies?.size)
    }

    @Test
    fun getTvShowData() {
        val tvShows = viewModel.getMovieData()
        assertNotNull(tvShows)
        assertEquals(11, tvShows?.size)
    }
}