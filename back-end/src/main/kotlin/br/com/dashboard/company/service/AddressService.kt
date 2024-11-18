package br.com.dashboard.company.service

//import br.com.dashboard.company.entities.address.Address
//import br.com.dashboard.company.repository.AddressRepository
//import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
//import br.com.dashboard.company.vo.address.AddressRequestVO
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//
//@Service
//class AddressService {
//
//    @Autowired
//    private lateinit var addressRepository: AddressRepository
//
//    fun saveAddress(
//        address: AddressRequestVO? = null
//    ): Address {
//        val addressResult: Address = parseObject(address, Address::class.java)
//        return parseObject(addressRepository.save(addressResult), Address::class.java)
//    }
//}
