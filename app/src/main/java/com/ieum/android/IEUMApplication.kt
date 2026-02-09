package com.ieum.android

import android.app.Application
import android.os.StrictMode
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class IEUMApplication : Application(), SingletonImageLoader.Factory {
    @Inject
    @NetworkSource(IEUMNetwork.Default)
    lateinit var defaultClient: HttpClient

    override fun onCreate() {
        super.onCreate()
        setStrictMode()
        initKakaoSdk()
        setTimber()
        warmUpKtorClient()
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()

    private fun setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .build()
            )
        }
    }

    private fun initKakaoSdk() {
        val originPolicy = StrictMode.allowThreadDiskReads()
        try {
            KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        } finally {
            StrictMode.setThreadPolicy(originPolicy)
        }
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Firebase Crashlytics
        }
    }

    private fun warmUpKtorClient() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                defaultClient.toString()
            } catch (e: Exception) {
                Timber.e(e, "Failed to warm up Ktor client")
            }
        }
    }
}