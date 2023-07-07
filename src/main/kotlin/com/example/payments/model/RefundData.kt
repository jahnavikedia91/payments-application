package com.example.payments.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "RefundData")
class RefundData {

    @Id
    @Column(name="refundId", unique=true, nullable=false)
    var refundId: String? = ""

    @Column(name="amount", nullable=false)
    var amount: Long = 0

//    @Column(name="currency", nullable=false)
//    var currency: String = ""

    @Column(name="paymentIntent", nullable=false)
    var paymentIntent: String = ""

    @Column(name="status", nullable=false)
    var status: String = ""

}