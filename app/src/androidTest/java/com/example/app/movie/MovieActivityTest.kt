package com.example.app.movie

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.app.movie.utils.EspressoIdlingResourceRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.runners.MethodSorters

/**
 * Large End-to-End test for the movie app.
 *
 */
@LargeTest
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieActivityTest {

  @get:Rule(order = 0)
  var hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val espressoIdlingResoureRule = EspressoIdlingResourceRule()

  @get:Rule(order = 2)
  val activityRule = ActivityScenarioRule(MovieActivity::class.java)


  @Before
  fun setUp() {
    hiltRule.inject()
  }

}