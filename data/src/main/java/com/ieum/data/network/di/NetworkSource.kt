package com.ieum.data.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NetworkSource(val network: IEUMNetwork)

enum class IEUMNetwork {
    Default, Address
}


