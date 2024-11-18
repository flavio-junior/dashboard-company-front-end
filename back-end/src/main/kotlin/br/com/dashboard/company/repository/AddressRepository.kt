package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.address.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long>
