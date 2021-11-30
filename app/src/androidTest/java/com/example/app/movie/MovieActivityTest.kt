package com.example.app.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.app.movie.data.MovieListResponse
import com.example.app.movie.data.source.local.MovieDatabase
import com.example.app.movie.ui.MovieListItemVH
import com.example.app.movie.utils.EspressoIdlingResourceRule
import com.example.app.movie.utils.TestData
import com.example.app.movie.utils.parseJsonToObject
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

/**
 * Large End-to-End test for the movie app.
 *
 */
@LargeTest
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieActivityTest {

  @Inject
  lateinit var movieDataBase: MovieDatabase

  @get:Rule(order = 0)
  var hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val espressoIdlingResoureRule = EspressoIdlingResourceRule()

  @get:Rule(order = 2)
  val activityRule = ActivityScenarioRule(MovieActivity::class.java)

  @Before
  fun setUp() {
    hiltRule.inject()
    activityRule.scenario
    movieDataBase.clearAllTables()
  }

  @Test
  fun `1_test_showMovieList_openDetail_addToFavorite_showFavoriteList`() {
    val testMovie =
      parseJsonToObject(TestData.rawJson, MovieListResponse::class.java).results[TestData.testIndex]

    onView(withId(R.id.rv_movie_list))
      .perform(scrollToPosition<MovieListItemVH>(TestData.testIndex))

    onView(withId(R.id.rv_movie_list))
      .perform(
        actionOnItemAtPosition<MovieListItemVH>(
          TestData.testIndex,
          click()
        )
      )

    onView(withId(R.id.ib_favorite)).perform(click())

    pressBack()

    onView(
      Matchers.allOf(
        withResourceName("bottom_tab_favorite"),
        isDescendantOfA(withId(R.id.bottom_nav_view))
      )
    ).perform(click())

    onView(
      Matchers.allOf(
        withId(R.id.tv_movie_title),
        isDescendantOfA(withId(R.id.rv_movie_fav))
      )
    ).check(matches(withText(testMovie.title)))
  }

}