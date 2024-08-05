package com.example.courseskoinapp.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.example.courseskoinapp.R
import com.example.courseskoinapp.ui.home.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author Muhamed Amin Hassan on 04,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */


fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun Fragment.invisibleNavigation() {
    val navigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_nav_view)
    navigationView.gone()
}

fun Fragment.visibleNavigation() {
    val navigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_nav_view)
    navigationView.show()
}