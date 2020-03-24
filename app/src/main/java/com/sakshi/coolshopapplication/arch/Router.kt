package com.sakshi.coolshopapplication.arch

import androidx.fragment.app.FragmentManager
import com.sakshi.coolshopapplication.R
import com.sakshi.coolshopapplication.view.display.DisplayFragment
import com.sakshi.coolshopapplication.view.login.LoginFragment

class Router(
    private val fragmentManager: FragmentManager
) {

    fun toLoginPage() {
        val issueListFragment = LoginFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, issueListFragment)
        transaction.commit()
    }

    fun toDisplayPage() {
        val displayFragment = DisplayFragment.newInstance()
        val transaction = fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, displayFragment)
            addToBackStack("ISSUE_DETAIL_FRAGMENT")
        }
        transaction.commit()
    }

}