package br.com.digital.store.features.networking.utils

import br.com.digital.store.features.networking.utils.NetworkingUtils.COMMON_MODULES
import org.koin.mp.KoinPlatform.getKoin

fun reloadViewModels() {
    val koin = getKoin()
    koin.unloadModules(COMMON_MODULES)
    koin.loadModules(COMMON_MODULES)
}