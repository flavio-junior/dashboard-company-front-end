package br.com.dashboard.company.service

import br.com.dashboard.company.entities.checkout.Checkout
import br.com.dashboard.company.repository.CheckoutRepository
import br.com.dashboard.company.vo.checkout.GeneralBalanceResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CheckoutService {

    @Autowired
    private lateinit var checkoutRepository: CheckoutRepository

    @Transactional
    fun saveCheckoutDetails(
        total: Double
    ) {
        checkoutRepository.save(Checkout(date = Date(), total = total))
    }

    @Transactional
    fun getGeneralBalance(
        date: Date
    ): GeneralBalanceResponseVO {
        return GeneralBalanceResponseVO(total = checkoutRepository.getGeneralBalance(date = date))
    }

    @Transactional
    fun getBalanceLast7Days(): GeneralBalanceResponseVO {
        return GeneralBalanceResponseVO(total = checkoutRepository.findBalanceLast7DaysNative())
    }

    @Transactional
    fun getBalanceCurrentMonth(): GeneralBalanceResponseVO {
        return GeneralBalanceResponseVO(total = checkoutRepository.findBalanceCurrentMonthNative())
    }

    @Transactional
    fun getBalanceCurrentYear(): GeneralBalanceResponseVO {
        return GeneralBalanceResponseVO(total = checkoutRepository.findBalanceCurrentYearNative())
    }
}
