package com.example.payments.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "PaymentIntentData")
class PaymentIntentData {
    @Id
    @Column(name="paymentId", unique=true, nullable=false)
    var paymentId: String? = ""

    @Column(name="amount", nullable=false)
    var amount: Long = 0

    @Column(name="currency", nullable=false)
    var currency: String = ""

    @Column(name="customerId",nullable=false)
    var customerId : String = ""

    @Column(name="status", nullable=false)
    var status : String = ""



}