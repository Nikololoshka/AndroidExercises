package com.vereshchagin.nikolay.android

import com.vereshchagin.nikolay.android.navigation.ScreenRoute
import com.vereshchagin.nikolay.android.presentation.CommonFragmentRoute

object Screens {

    val allScreens: List<ScreenRoute> = MutableList(3) { CommonFragmentRoute(it) }
}