package com.example.payments.controller

import com.example.payments.model.PaymentIntentData
import com.example.payments.model.RefundData
import com.example.payments.repository.PaymentIntentRepository
import com.example.payments.repository.RefundRepository
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.PaymentIntent
import com.stripe.model.Refund
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/refund")

class RefundController(
    private val PaymentIntentRepo : PaymentIntentRepository,
    private val RefundRepo : RefundRepository)
{

    @Value("\${stripe.apikey}")
    lateinit var stripeKey: String

    @PostMapping("/createRefund")
    @Throws(StripeException::class)
    fun create(@RequestBody data: RefundData): RefundData {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["amount"] = data.amount * 100
//        params["currency"] = data.currency
        params["payment_intent"] = data.paymentIntent

        val refund = Refund.create(params)
        data.refundId = refund.id
        data.status = refund.status
        RefundRepo.save(data)
        val paymentIntentApi = PaymentIntent.retrieve(
            data.paymentIntent
        )
        var updatedPayment : PaymentIntentData = PaymentIntentRepo.findById(data.paymentIntent).orElse(null)
        updatedPayment.status = "refunded"
        PaymentIntentRepo.save(updatedPayment)
        return data
    }

    @GetMapping("/getRefundById/{id}")
    @Throws(StripeException::class)
    fun getRefundById(@PathVariable("id") RefundId : String) : RefundData
    {
        Stripe.apiKey = stripeKey
        val refund = Refund.retrieve(RefundId)

        var data = RefundData()
        data.refundId = refund.id
        data.amount = refund.amount/100
        data.paymentIntent = refund.paymentIntent
        data.status = refund.status

        return data
    }

    @GetMapping("/ListAllRefunds")
    @Throws(StripeException::class)
    fun getAllPayments() : MutableList<RefundData>
    {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val refunds = Refund.list(params)
        val allRefunds: MutableList<RefundData> = ArrayList()
        for (i in refunds.data.indices) {
            var refund = RefundData()
            refund.refundId= refunds.data[i].id
            refund.amount = refunds.data[i].amount / 100
            refund.paymentIntent = refunds.data[i].paymentIntent
            refund.status = refunds.data[i].status
            allRefunds.add(refund)

        }
        return allRefunds
    }

    @GetMapping("/ListAllRefundsByPaymentIntentId/{id}")
    @Throws(StripeException::class)
    fun getAllPaymentsByPaymentId(@PathVariable("id") PaymentIntentId : String) : MutableList<RefundData>
    {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3
        params["payment_intent"] = PaymentIntentId

        val refunds = Refund.list(params)
        val allRefunds: MutableList<RefundData> = ArrayList()
        for (i in refunds.data.indices) {
            var refund = RefundData()
            refund.refundId= refunds.data[i].id
            refund.amount = refunds.data[i].amount / 100
            refund.paymentIntent = PaymentIntentId
            refund.status = refunds.data[i].status
            allRefunds.add(refund)

        }
        return allRefunds
    }




}