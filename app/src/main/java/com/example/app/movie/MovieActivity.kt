package com.example.app.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    bottom_nav_view.setupWithNavController(navController)
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
      if (destination.id == R.id.movie_detail) {
        bottom_nav_view.visibility = View.GONE
      } else {
        bottom_nav_view.visibility = View.VISIBLE
      }
    }
  }
}