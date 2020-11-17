package com.acsl.moviex.ui.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.acsl.moviex.data.DataDummy
import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.data.source.DataRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository : DataRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = DataDummy.generateDummyMovies()
        val courses = MutableLiveData<List<MovieEntity>>()
        courses.value = dummyMovies


        `when`(repository.getAllMovies()).thenReturn(courses)
        val courseEntities = viewModel.getAllMovies().value
        Mockito.verify<DataRepository>(repository).getAllMovies()
        Assert.assertNotNull(courseEntities)
        Assert.assertEquals(1, courseEntities?.size)

        viewModel.getAllMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = DataDummy.generateDummyTvShows()
        val courses = MutableLiveData<List<MovieEntity>>()
        courses.value = dummyTvShows


        `when`(repository.getAllTvShows()).thenReturn(courses)
        val courseEntities = viewModel.getAllTvShows().value
        Mockito.verify<DataRepository>(repository).getAllTvShows()
        Assert.assertNotNull(courseEntities)
        Assert.assertEquals(1, courseEntities?.size)

        viewModel.getAllTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShows)
    }
}