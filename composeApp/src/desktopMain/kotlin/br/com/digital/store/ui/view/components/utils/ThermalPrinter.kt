package br.com.digital.store.ui.view.components.utils

import org.slf4j.LoggerFactory
import java.io.InputStream
import javax.print.DocFlavor
import javax.print.PrintException
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.SimpleDoc
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.HashPrintServiceAttributeSet
import javax.print.attribute.standard.PrinterName

class ThermalPrinter {

    private val logger = LoggerFactory.getLogger(ThermalPrinter::class.java)

    fun getPrints(): List<String> {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)
        return printServices.map { it.name }
    }

    fun printInputStream(inputStream: InputStream, printerName: String) {
        logger.info("Iniciando impressão na impressora: $printerName")
        val printService = findPrintServiceByName(printerName)
        if (printService != null) {
            try {
                logger.info("Criando SimpleDoc...")
                val docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE
                val doc = SimpleDoc(inputStream, docFlavor, null)
                logger.info("SimpleDoc criado.")

                logger.info("Criando atributos de impressão...")
                val printRequestAttributeSet = HashPrintRequestAttributeSet()
                val printServiceAttributeSet = HashPrintServiceAttributeSet()
                printServiceAttributeSet.add(PrinterName(printService.name, null))
                logger.info("Atributos de impressão criados.")

                logger.info("Obtendo serviço de impressão pelo nome...")
                val selectedPrintService = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet)
                    .firstOrNull { it.name == printerName }
                logger.info("Serviço de impressão obtido: ${selectedPrintService?.name}")

                if (selectedPrintService != null) {
                    logger.info("Criando trabalho de impressão...")
                    val printJob = selectedPrintService.createPrintJob()
                    logger.info("Trabalho de impressão criado.")

                    logger.info("Iniciando impressão...")
                    printJob.print(doc, printRequestAttributeSet)
                    logger.info("Impressão concluída.")
                } else {
                    logger.warn("Impressora térmica $printerName não encontrada.")
                }
            } catch (e: PrintException) {
                logger.error("Erro ao imprimir", e)
            } catch (e: IllegalArgumentException) {
                logger.error("Erro ao criar SimpleDoc", e)
            }
        } else {
            logger.warn("Impressora térmica $printerName não encontrada.")
        }
    }

    private fun findPrintServiceByName(printerName: String): PrintService? {
        logger.info("Procurando impressora com o nome: $printerName")
        val attributes = HashPrintServiceAttributeSet()
        val printServices = PrintServiceLookup.lookupPrintServices(null, attributes)
        logger.info("Impressoras encontradas: ${printServices.map { it.name }}")
        val foundPrintService = printServices.firstOrNull { it.name == printerName }
        if (foundPrintService != null) {
            logger.info("Impressora encontrada: ${foundPrintService.name}")
        } else {
            logger.warn("Impressora $printerName não encontrada na lista de impressoras.")
        }
        return foundPrintService
    }
}
