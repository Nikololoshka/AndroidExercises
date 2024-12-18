package com.vereshchagin.nikolay.android

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vereshchagin.nikolay.android.navigation.Navigator
import com.vereshchagin.nikolay.android.navigation.Router
import com.vereshchagin.nikolay.android.presentation.CommonFragment
import com.vereshchagin.nikolay.android.presentation.CommonFragmentRoute
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    /**
     * Дано: Приложение с тремя экранами на базе Fragment, на каждом экране есть 2 кнопки -
     * для перехода на следующий экран и предыдущий. Реализовать базовый роутер для
     * навигации между фрагментами без использования дополнительных библиотек.
     */

    @Inject
    lateinit var router: Router

    private val navigator by lazy { Navigator(R.id.container, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        MainApplication.Instance.appComponent.inject(this)

        setContentView(R.layout.activity_main)
        setupNavigation(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            router.registry.apply {
                register<CommonFragmentRoute> { CommonFragment.newInstance(index) }
            }

            router.navigate(Screens.allScreens.first())
        }

        onBackPressedDispatcher.addCallback(this) { if (!router.popBack()) finish() }
    }

    override fun onStart() {
        super.onStart()
        router.setNavigator(navigator)
    }

    override fun onStop() {
        super.onStop()
        router.removeNavigator()
    }
}