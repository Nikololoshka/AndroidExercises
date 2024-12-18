package com.vereshchagin.nikolay.android.navigation

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

class Navigator(
    @IdRes private val containerId: Int,
    activity: AppCompatActivity
) {

    val currentBackStackCount: Int get() = transactionExecutor.backStackEntryCount

    private val transactionExecutor: FragmentManager = activity.supportFragmentManager

    fun commit(transaction: FragmentTransaction.(containerId: Int) -> Unit) {
        transactionExecutor.commit { transaction(containerId) }
    }

    fun popBack(backCount: Int) {
        repeat(backCount) { transactionExecutor.popBackStack() }
    }
}