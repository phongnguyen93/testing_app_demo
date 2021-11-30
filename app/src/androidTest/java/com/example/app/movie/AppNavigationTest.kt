package com.example.app.movie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.app.movie.utils.EspressoIdlingResourceRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Large End-to-End test for the movie app.
 *
 */
@LargeTest
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppNavigationTest {

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
  }

  @Test
  fun `1_test_movieListFragment_should_visible_onAppLaunch`() {
    onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()))
  }

  @Test
  fun `2_test_bottomNavigation_on_favoriteTab_click_navigateTo_MovieFavoriteFragment`() {
    onView(
      allOf(
        withResourceName("bottom_tab_favorite"),
        isDescendantOfA(withId(R.id.bottom_nav_view))
      )
    ).perform(click())
    onView(withId(R.id.rv_movie_fav)).check(matches(isDisplayed()))
  }

  @Test
  fun `3_test_bottomNavigation_on_homeTab_click_navigateTo_MovieListFragment`() {
    onView(
      allOf(
        withResourceName("bottom_tab_favorite"),
        isDescendantOfA(withId(R.id.bottom_nav_view))
      )
    ).perform(click())
    onView(
      allOf(
        withResourceName("bottom_tab_home"),
        isDescendantOfA(withId(R.id.bottom_nav_view))
      )
    ).perform(click())
    onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()))
  }

  @Test
  fun `4_test_movieListFragment_onItemClick_should_navigateTo_MovieDetailFragment`() {
    onView(withId(R.id.rv_movie_list))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))
    onView(withId(R.id.rv_movie_list))
      .perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
          0,
          click()
        )
      )
    onView(withId(R.id.tv_movie_detail_title)).check(matches(isDisplayed()))
  }

  @Test
  fun `5_test_movieDetailFragment_backPress_should_navigateBack_MovieListFragment_correctly`() {
    onView(withId(R.id.rv_movie_list))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))
    onView(withId(R.id.rv_movie_list))
      .perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
          0,
          click()
        )
      )
    pressBack()
    onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()))
  }
}