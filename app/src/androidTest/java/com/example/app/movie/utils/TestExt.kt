package com.example.app.movie.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.app.movie.HiltTestActivity
import com.example.app.movie.R
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi


const val THEME_EXTRAS_BUNDLE_KEY = "androidx.fragment.app.testing.FragmentScenario" +
    ".EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
  fragmentArgs: Bundle? = null,
  @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
  crossinline action: Fragment.() -> Unit = {}
) {
  val startActivityIntent = Intent.makeMainActivity(
    ComponentName(
      ApplicationProvider.getApplicationContext(),
      HiltTestActivity::class.java
    )
  ).putExtra(THEME_EXTRAS_BUNDLE_KEY, themeResId)

  ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
    val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
      Preconditions.checkNotNull(T::class.java.classLoader),
      T::class.java.name
    )
    fragment.arguments = fragmentArgs
    activity.supportFragmentManager
      .beginTransaction()
      .add(android.R.id.content, fragment, "")
      .commitNow()

    fragment.action()
  }
}


@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
  fragmentArgs: Bundle? = null,
  @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
  factory: FragmentFactory,
  crossinline action: Fragment.() -> Unit = {}
) {
  val startActivityIntent = Intent.makeMainActivity(
    ComponentName(
      ApplicationProvider.getApplicationContext(),
      HiltTestActivity::class.java
    )
  ).putExtra(THEME_EXTRAS_BUNDLE_KEY, themeResId)

  ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
    activity.supportFragmentManager.fragmentFactory = factory
    val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
      Preconditions.checkNotNull(T::class.java.classLoader),
      T::class.java.name
    )
    fragment.arguments = fragmentArgs

    activity.supportFragmentManager
      .beginTransaction()
      .add(android.R.id.content, fragment, "")
      .commitNow()

    fragment.action()
  }
}

fun <T> parseJsonToObject(jsonString: String, className: Class<T>): T {
  return Gson().fromJson<T>(jsonString, className)
}



















