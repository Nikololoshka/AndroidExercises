package com.vereshchagin.nikolay.android.navigation

class Router {

    private var currentNavigator: Navigator? = null

    private val buffer: MutableList<NavigationCommand> = arrayListOf()

    val registry: ScreenRouteRegistry = ScreenRouteRegistry()

    fun navigate(route: ScreenRoute) {
        val destination = registry.get(route)
        val command = NavigationCommand.NavigateTo(destination)
        processCommand(command)
    }

    private fun processCommand(
        command: NavigationCommand
    ) {
        currentNavigator.also { navigator ->
            if (navigator == null) {
                buffer.add(command)
                return
            }
            processCommand(navigator, command)
        }
    }

    private fun processCommand(
        navigator: Navigator,
        command: NavigationCommand,
    ) {
        when (command) {
            is NavigationCommand.NavigateTo -> navigate(navigator, command)
        }
    }

    private fun navigate(
        navigator: Navigator,
        command: NavigationCommand.NavigateTo
    ) {
        navigator.commit { containerId ->
            setReorderingAllowed(true)
            replace(containerId, command.destination.creator(), command.destination.name)
        }
    }

    fun setNavigator(navigator: Navigator) {
        currentNavigator = navigator

        if (buffer.isNotEmpty()) {
            buffer.forEach { command -> processCommand(navigator, command) }
            buffer.clear()
        }
    }

    fun removeNavigator() {
        currentNavigator = null
    }

    fun popBack(backCount: Int = 1): Boolean {
        currentNavigator?.let { navigator ->
            if (navigator.currentBackStackCount > backCount) {
                navigator.popBack(backCount)
                return true
            }
        }
        return false
    }
}