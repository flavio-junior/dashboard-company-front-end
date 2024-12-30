package br.com.digital.store.features.networking.resources

import br.com.digital.store.features.networking.resources.NetworkingUtils.COMMON_MODULES
import org.koin.mp.KoinPlatform.getKoin

fun reloadViewModels() {
    val koin = getKoin()
    koin.unloadModules(COMMON_MODULES)
    koin.loadModules(COMMON_MODULES)
}