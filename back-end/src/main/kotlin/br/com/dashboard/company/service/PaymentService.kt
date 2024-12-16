package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.entities.payment.Payment
import br.com.dashboard.company.repository.PaymentRepository
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.order.CloseOrderRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class PaymentService {

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    fun updatePayment(
        closeOrder: CloseOrderRequestVO,
        order: Order
    ) {
        val paymentResult: Payment = parseObject(closeOrder, Payment::class.java)
        paymentResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        paymentResult.total = order.total
        paymentResult.order = order
        paymentRepository.save(paymentResult)
    }
}
