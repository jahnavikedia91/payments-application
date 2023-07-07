package com.example.payments.repository

import com.example.payments.model.CustomerData
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerDataRepo : JpaRepository<CustomerData, Int> {
}