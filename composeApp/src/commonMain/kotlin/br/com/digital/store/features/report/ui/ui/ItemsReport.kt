package br.com.digital.store.features.report.ui.ui

import br.com.digital.store.features.report.utils.ReportUtils.CREATE_NEW_REPORT
import br.com.digital.store.features.report.utils.ReportUtils.DETAILS_REPORT
import br.com.digital.store.features.report.utils.ReportUtils.LIST_REPORTS

enum class ItemsReport(val text: String) {
    ListReports(text = LIST_REPORTS),
    DetailsReport(text = DETAILS_REPORT),
    CreateReport(text = CREATE_NEW_REPORT)
}
