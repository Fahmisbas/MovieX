package com.acsl.moviex.ui.navigation.utils

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import org.mockito.Mockito
import java.util.concurrent.Executor

object PagedListUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val uiThread = Mockito.mock(UiThreadExecutor::class.java)
        return PagedList.Builder(ListDataSource(list), config)
            .setNotifyExecutor(uiThread)
            .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            .build()
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(1)
        .build()


    open class UiThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }

    class ListDataSource<T>(private val items: List<T>) : PositionalDataSource<T>() {
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}




