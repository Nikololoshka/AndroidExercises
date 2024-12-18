package com.vereshchagin.nikolay.android.navigation

sealed interface NavigationCommand {
    data class NavigateTo(val destination: Destination): NavigationCommand
}