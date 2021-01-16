package com.example.boni_football_score_viewer

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val RESULT_MATCH = "result_match"
const val UPCOMING_MATCH = "upcoming_match"
const val SAVED_MATCH = "saved_match"
const val MENU = "menu"

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private val onNavigationSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
                item -> when (item.itemId) {
            R.id.match_result -> {
                item.isCheckable = true
                val prevMatchFragment = MainMatchFragment.newInstance()
                openFragment(prevMatchFragment, RESULT_MATCH)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.upcoming_match -> {
                item.isCheckable = true
                val prevMatchFragment = MainMatchFragment.newInstance()
                openFragment(prevMatchFragment, UPCOMING_MATCH)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.saved_match -> {
                item.isCheckable = true
                val prevMatchFragment = MainMatchFragment.newInstance()
                openFragment(prevMatchFragment, SAVED_MATCH)
                return@OnNavigationItemSelectedListener  true
            }
        }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(this)
        shouldDisplayHomeUp()
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationSelectedListener)
        bottomNavigation.selectedItemId = R.id.match_result
    }

    private fun openFragment(fragment: Fragment, menu : String) {

        var bundle = Bundle()
        bundle.putString(MENU, menu)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {

            replace(R.id.container, fragment)
            commit()

        }
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true

    }

    fun shouldDisplayHomeUp() {
        val canback = supportFragmentManager.backStackEntryCount > 1
        supportActionBar?.setDisplayHomeAsUpEnabled(canback)
    }


}