package ua.railian.mvi.sample

import android.app.Application
import org.koin.core.context.startKoin
import ua.railian.mvi.sample.feature.auth.di.authFeatureModule
import ua.railian.mvi.sample.feature.home.di.homeFeatureModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                authFeatureModule,
                homeFeatureModule,
            )
        }
    }
}