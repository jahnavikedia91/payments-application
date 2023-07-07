package com.example.payments.repository;

import com.example.payments.model.RefundData
import org.springframework.data.jpa.repository.JpaRepository

interface RefundRepository : JpaRepository<RefundData, String> {
}