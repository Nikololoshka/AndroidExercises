package com.vereshchagin.nikolay.dagger.core.di.injector

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun <C> ViewModelStoreOwner.scopedComponent(
    componentProvider: () -> C
): Lazy<C> {
    return ScopedComponentProperty(this, componentProvider)
}

fun Fragment.appDependenciesProvider(): AppDependenciesProvider {
    return (requireActivity().application as App).appDependenciesProvider()
}


class ScopedComponentProperty<C>(
    private val storeOwner: ViewModelStoreOwner,
    private val componentProvider: () -> C
) : Lazy<C> {

    private var component: C? = null

    @Suppress("UNCHECKED_CAST")
    override val value: C
        get() {
            val currentComponent = component
            if (currentComponent != null) return currentComponent

            val viewModels = ViewModelProvider(
                storeOwner,
                ScopedComponentHolderFactory(componentProvider)
            )
            val componentHolder = viewModels[ScopedComponentHolder::class.java]
            val createdComponent = componentHolder.component as C
            component = createdComponent

            return createdComponent
        }

    override fun isInitialized(): Boolean = component != null
}

class ScopedComponentHolderFactory<C>(
    private val factory: () -> C
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScopedComponentHolder(factory()) as T
    }
}

class ScopedComponentHolder<T>(val component: T) : ViewModel()