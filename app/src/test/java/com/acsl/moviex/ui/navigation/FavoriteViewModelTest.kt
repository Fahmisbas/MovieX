package com.acsl.moviex.ui.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.ui.tabs.favorite.FavoriteViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AppDataRepository

    @Mock
    private lateinit var observer: Observer<PagedList<DataEntity>>

    @Mock
    private lateinit var pagedList: PagedList<DataEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun getAllFavoriteMovies() {
        val dummyData = pagedList
        Mockito.`when`(dummyData.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<DataEntity>>()
        movies.value = dummyData

        Mockito.`when`(repository.getAllFavoriteMovies()).thenReturn(movies)
        val movieEntities = viewModel.getAllFavoriteMovies().value
        Mockito.verify(repository).getAllFavoriteMovies()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(5, movieEntities?.size)

        viewModel.getAllFavoriteMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyData)
    }

    @Test
    fun getAllFavoriteTvShows() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<DataEntity>>()
        movies.value = dummyTvShows

        Mockito.`when`(repository.getAllFavoriteTvShows()).thenReturn(movies)
        val tvShowEntities = viewModel.getAllFavoriteTvShows().value
        Mockito.verify(repository).getAllFavoriteTvShows()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)

        viewModel.getAllFavoriteTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShows)

    }
}