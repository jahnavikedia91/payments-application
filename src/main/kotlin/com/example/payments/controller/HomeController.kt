package com.example.payments.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/payments")
class HomeController {

   @Value("\${stripe.apikey}")
   lateinit var stripeKey: String

    @GetMapping("/")
    fun home() {
        println("hello "+stripeKey)

    }

}