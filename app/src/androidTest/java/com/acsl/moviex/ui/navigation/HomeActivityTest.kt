package com.acsl.moviex.ui.navigation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.acsl.moviex.R
import com.acsl.moviex.data.repositories.MovieRepository
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = MovieRepository().getMovieData()

    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(allOf(withId(R.id.rv_tabs), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies?.size!!))
    }

    @Test
    fun loadMovieCourse() {
        onView(allOf(withId(R.id.rv_tabs), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_movie_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_name)).check(matches(withText(dummyMovies?.get(0)?.movieName)))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyMovies?.get(0)?.overview)))
    }
}



