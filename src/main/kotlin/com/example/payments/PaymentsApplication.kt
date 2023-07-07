package com.example.payments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class PaymentsApplication

fun main(args: Array<String>) {
	runApplication<PaymentsApplication>(*args)
}


