package com.example.payments.repository;

import com.example.payments.model.PaymentIntentData
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentIntentRepository : JpaRepository<PaymentIntentData, String> {
}