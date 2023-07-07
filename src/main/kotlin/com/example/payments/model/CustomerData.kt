package com.example.payments.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "Customer")
class CustomerData {
    @Id
    @Column(name="customerId", unique=true, nullable=false)
    var customerId: String? = ""

    @NotBlank(message = "username shouldn't be null")
    @Column(name="name", unique=true, nullable=true)
    var name: String = ""

    @Email(message = "invalid email address")
    @NotBlank(message="email shouldn't be null")
    @Column(name="email", unique=true, nullable=true)
    var email: String = ""

//    @OneToMany
//    @Column(name="paymentId",nullable=true)
//    var PaymentIntentData: MutableSet<PaymentIntentData> = HashSet()
}