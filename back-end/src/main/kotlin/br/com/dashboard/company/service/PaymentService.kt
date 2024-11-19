package br.com.dashboard.company.service

import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.PaymentRepository
import br.com.dashboard.company.vo.order.CloseOrderRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PaymentService {

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    fun updatePayment(
        closeOrder: CloseOrderRequestVO
    ) {
        paymentRepository.findById(closeOrder.id).orElseThrow { ResourceNotFoundException(message = PAYMENT_NOT_FOUND) }
        paymentRepository.updatePayment(
            id = closeOrder.id,
            status = closeOrder.status,
            type = closeOrder.type
        )
    }

    companion object {
        const val PAYMENT_NOT_FOUND = "Payment not found!"
    }
}
