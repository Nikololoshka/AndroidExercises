package com.vereshchagin.nikolay.android.navigation

import androidx.fragment.app.Fragment

class Destination(
    val name: String,
    val creator: () -> Fragment
)