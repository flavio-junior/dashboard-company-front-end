package br.com.digital.store.ui.view.settings.utils

import java.awt.Desktop
import java.net.URI

fun openUrlInBrowser(url: String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            val desktop = Desktop.getDesktop()
            desktop.browse(URI(url))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
