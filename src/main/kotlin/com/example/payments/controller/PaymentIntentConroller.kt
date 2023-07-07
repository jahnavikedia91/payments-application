package com.example.payments.controller

import com.example.payments.model.PaymentIntentData
import com.example.payments.service.PaymentIntentService
import com.stripe.exception.StripeException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/payments")
class PaymentIntentConroller(private val PaymentIntentService :PaymentIntentService) {

    @PostMapping("/createPaymentIntent")
    @Throws(StripeException::class)
    fun create(@RequestBody data: PaymentIntentData): PaymentIntentData {
        PaymentIntentService.createPaymentIntent(data)
        return data
    }

    @GetMapping("/getPaymentIntentById/{id}")
    @Throws(StripeException::class)
    fun getPaymentById(@PathVariable("id") paymentIntentId: String): PaymentIntentData {
        return PaymentIntentService.getPaymentIntentById(paymentIntentId)
    }

    @PutMapping("/updatePaymentIntentById/{id}")
    @Throws(StripeException::class)
    fun updatePaymentIntent(@PathVariable("id") paymentIntentId : String,@RequestBody data: PaymentIntentData) : PaymentIntentData
    {
        PaymentIntentService.updatePaymentIntentById(paymentIntentId,data)
        return data
    }

    @PostMapping("/confirmPaymentIntent/{id}/confirm")
    @Throws(StripeException::class)
    fun confirm(@PathVariable("id") paymentIntentId : String): PaymentIntentData
    {
        return PaymentIntentService.confirmPaymentIntent(paymentIntentId)
    }

    @PostMapping("/confirmPaymentIntent/{id}/cancel")
    @Throws(StripeException::class)
    fun cancel(@PathVariable("id") paymentIntentId : String): PaymentIntentData
    {
        return PaymentIntentService.cancelPaymentIntent(paymentIntentId)
    }

    @GetMapping("/ListPaymentIntent")
    @Throws(StripeException::class)
    fun getAllPayments() : MutableList<PaymentIntentData>
    {
        return PaymentIntentService.ListAllPaymentIntent()
    }

    @GetMapping("/ListPaymentIntentByCustomerId/{id}")
    @Throws(StripeException::class)
    fun getAllPaymentsByCustomerId(@PathVariable("id") customerId : String) : MutableList<PaymentIntentData>
    {
        return PaymentIntentService.listPaymentIntentByCustomerId(customerId)
    }
}