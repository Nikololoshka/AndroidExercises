package com.vereshchagin.nikolay.android.navigation

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

private typealias RouteKey = KClass<out ScreenRoute>
private typealias ScreenFactory = ScreenRoute.() -> Fragment

class ScreenRouteRegistry {

    val factories: MutableMap<RouteKey, ScreenFactory> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : ScreenRoute> register(noinline factory: T.() -> Fragment) {
        factories[T::class] = factory as ScreenFactory
    }

    fun get(route: ScreenRoute): Destination {
        val factory = factories[route::class]
            ?: error("ScreenRoute not registered: ${route::class.simpleName}")
        return Destination(route::class.java.simpleName) { route.factory() }
    }
}