package com.acsl.moviex.ui.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.ui.navigation.dummy.DataDummy
import com.acsl.moviex.ui.navigation.utils.PagedListUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AppDataRepository
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var observer: Observer<PagedList<DataEntity>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getMovies() {
        val mutableDummyData = MutableLiveData<PagedList<DataEntity>>()
        val dummyContent = PagedListUtil.mockPagedList(DataDummy.generateDummyMovies())
        mutableDummyData.postValue(dummyContent)
        val dummyData: LiveData<PagedList<DataEntity>> = mutableDummyData


        `when`(repository.getAllMovies()).thenReturn(dummyData)
        val courseEntities = viewModel.getAllMovies().value
        verify(repository).getAllMovies()
        Assert.assertNotNull(courseEntities)
        Assert.assertEquals(1, courseEntities?.size)

        viewModel.getAllMovies().observeForever(observer)
        verify(observer).onChanged(dummyContent)
    }

    @Test
    fun getTvShows() {
        val mutableDummyData = MutableLiveData<PagedList<DataEntity>>()
        val dummyData: LiveData<PagedList<DataEntity>> = mutableDummyData
        val dummyContent = PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows())
        mutableDummyData.postValue(dummyContent)

        `when`(repository.getAllTvShows()).thenReturn(dummyData)
        val courseEntities = viewModel.getAllTvShows().value
        verify(repository).getAllTvShows()
        Assert.assertNotNull(courseEntities)
        Assert.assertEquals(1, courseEntities?.size)

        viewModel.getAllTvShows().observeForever(observer)
        verify(observer).onChanged(dummyContent)
    }
}

