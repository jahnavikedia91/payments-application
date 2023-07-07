package com.example.payments.service

import com.example.payments.model.PaymentIntentData
import com.example.payments.repository.PaymentIntentRepository
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class PaymentIntentService(private val PaymentIntentRepo : PaymentIntentRepository) {
    @Value("\${stripe.apikey}")
    lateinit var stripeKey: String

    fun createPaymentIntent(data: PaymentIntentData) {
        Stripe.apiKey = stripeKey

        val paymentMethodTypes: MutableList<Any> = ArrayList()
        paymentMethodTypes.add("card")
        val params: MutableMap<String, Any> = HashMap()
        params["amount"] = data.amount * 100
        params["currency"] = data.currency
        params["payment_method_types"] = paymentMethodTypes
        params["customer"] = data.customerId
        params["description"] = "Stripe test"

        val paymentIntent = PaymentIntent.create(params)
        data.paymentId = paymentIntent.id
        data.status = paymentIntent.status
        PaymentIntentRepo.save(data)
    }

    fun getPaymentIntentById(paymentIntentId: String): PaymentIntentData {
        Stripe.apiKey = stripeKey
        val paymentIntent = PaymentIntent.retrieve(
            paymentIntentId
        )
        var payment = PaymentIntentData()
        payment.paymentId = paymentIntentId
        payment.amount = paymentIntent.amount/100
        payment.currency = paymentIntent.currency
        payment.customerId = paymentIntent.customer
        return payment
    }

    fun updatePaymentIntentById(paymentIntentId: String, data: PaymentIntentData) {
        Stripe.apiKey = stripeKey
        val paymentIntent = PaymentIntent.retrieve(
            paymentIntentId
        )

//        val metadata: MutableMap<String, Any> = HashMap()
//        metadata["order_id"] = "6735"
        val params: MutableMap<String, Any> = HashMap()
        params["amount"] = data.amount * 100
        params["currency"] = data.currency
        params["customer"] = data.customerId

        val updatedPaymentIntent = paymentIntent.update(params)
        data.paymentId = paymentIntentId
        data.status = updatedPaymentIntent.status
        PaymentIntentRepo.save(data)
    }

    fun confirmPaymentIntent(paymentIntentId: String): PaymentIntentData {
        Stripe.apiKey = stripeKey
        val paymentIntent = PaymentIntent.retrieve(
            paymentIntentId
        )

        val params: MutableMap<String, Any> = HashMap()
        params["payment_method"] = "pm_card_visa"

        val updatedPaymentIntent = paymentIntent.confirm(params)
        var updatedPayment : PaymentIntentData = PaymentIntentRepo.findById(paymentIntentId).orElse(null)
        updatedPayment.status = updatedPaymentIntent.status
        PaymentIntentRepo.save(updatedPayment)
        return updatedPayment
    }

    fun cancelPaymentIntent(paymentIntentId: String): PaymentIntentData {
        Stripe.apiKey = stripeKey
        val paymentIntent = PaymentIntent.retrieve(
            paymentIntentId
        )
        val updatedPaymentIntent = paymentIntent.cancel()

        var updatedPayment : PaymentIntentData = PaymentIntentRepo.findById(paymentIntentId).orElse(null)
        updatedPayment.status = updatedPaymentIntent.status
        PaymentIntentRepo.save(updatedPayment)
        return updatedPayment
    }

    fun ListAllPaymentIntent(): MutableList<PaymentIntentData> {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val paymentIntents = PaymentIntent.list(params)
        val allPayments: MutableList<PaymentIntentData> = ArrayList()
        for (i in paymentIntents.data.indices) {
            val paymentIntent = PaymentIntentData()
            paymentIntent.paymentId = paymentIntents.data[i].id
            paymentIntent.amount = paymentIntents.data[i].amount
            paymentIntent.currency = paymentIntents.data[i].currency
            paymentIntent.customerId = paymentIntents.data[i].customer
            paymentIntent.status = paymentIntents.data[i].status
            allPayments.add(paymentIntent)
        }
        return  allPayments
    }

    fun listPaymentIntentByCustomerId(customerId: String): MutableList<PaymentIntentData> {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3
        params["customer"] = customerId

        val paymentIntents = PaymentIntent.list(params)
        val allPayments: MutableList<PaymentIntentData> = ArrayList()
        for (i in paymentIntents.data.indices) {
            val paymentIntent = PaymentIntentData()
            paymentIntent.paymentId = paymentIntents.data[i].id
            paymentIntent.amount = paymentIntents.data[i].amount
            paymentIntent.currency = paymentIntents.data[i].currency
            paymentIntent.customerId = paymentIntents.data[i].customer
            paymentIntent.status = paymentIntents.data[i].status
            allPayments.add(paymentIntent)
        }
        return  allPayments
    }


}