package com.my.notes

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.my.notes.observer.EventManager
import com.my.notes.ui.AboutFragment
import com.my.notes.ui.ExitDialogFragment
import com.my.notes.ui.SettingsFragment
import com.my.notes.ui.StartFragment

class MainActivity : AppCompatActivity() {

    var eventManager: EventManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, StartFragment())
            .commit()
        initDrawer()
        eventManager = EventManager()
    }

    private fun initDrawer() {
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_about -> {
                    openAboutFragment()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_settings -> {
                    openSettingsFragment()
                    makeMessage()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_theme, R.id.nav_share, R.id.nav_send -> {
                    makeMessage()
                    return@setNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun openSettingsFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, SettingsFragment())
        fragmentCommit(fragmentTransaction)
    }

    private fun fragmentCommit(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.addToBackStack("")
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    private fun makeMessage() {
        Toast.makeText(
            applicationContext,
            R.string.plug_message, Toast.LENGTH_SHORT
        ).show()
    }

    private fun openAboutFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, AboutFragment())
        fragmentCommit(fragmentTransaction)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            ExitDialogFragment().show(supportFragmentManager, "DialogTAG")
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}