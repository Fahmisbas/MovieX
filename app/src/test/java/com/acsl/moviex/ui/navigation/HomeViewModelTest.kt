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
import com.acsl.moviex.ui.tabs.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
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
        val dummyData: LiveData<PagedList<DataEntity>> = mutableDummyData
        GlobalScope.launch(Dispatchers.IO) {
            val dummyContent = PagedListUtil.mockPagedList(DataDummy.generateDummyMovies())
            mutableDummyData.postValue(dummyContent)
            launch(Dispatchers.Main) {
                `when`(repository.getAllMovies()).thenReturn(dummyData)
                val courseEntities = viewModel.getAllMovies().value
                verify(repository).getAllMovies()
                Assert.assertNotNull(courseEntities)
                Assert.assertEquals(1, courseEntities?.size)

                viewModel.getAllMovies().observeForever(observer)
                verify(observer).onChanged(dummyContent)
            }
        }
    }


}