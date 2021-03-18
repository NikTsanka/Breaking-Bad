package com.ntsan.breakingbad.ui.fragments.navHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.databinding.NavHomeScreenBinding

class NavHomeFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var binding: NavHomeScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NavHomeScreenBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.homeNavTabBar?.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val controller = Navigation.findNavController(binding?.homeNavContainer!!)
        when (item.itemId) {

            R.id.home -> {
                controller.navigate(R.id.show_home)
            }
            R.id.search -> {

            }
            R.id.savedItem -> {

            }
            R.id.profile -> {
                controller.navigate(R.id.show_profile)
            }
        }
        return true
    }
}