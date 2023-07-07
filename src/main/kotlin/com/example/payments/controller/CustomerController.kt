package com.example.payments.controller

import com.example.payments.exception.UserNotFoundException
import com.example.payments.model.CustomerData
import com.example.payments.repository.CustomerDataRepo
import com.example.payments.service.CustomerDataService
import com.stripe.exception.StripeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/customer")
class CustomerController (private val CustomerDataRepo : CustomerDataRepo, private val CustomerDataService : CustomerDataService){
    @Value("\${stripe.apikey}")
    lateinit var stripeKey: String

    @GetMapping("/")
    fun create() {
        println("hello")
    }

    @PostMapping("/createCustomer")
    @Throws(StripeException::class)
    fun create(@RequestBody @Valid data: CustomerData): CustomerData {
        CustomerDataService.createCustomer(data)
        return data
    }

    @GetMapping("/getCustomerById/{customerId}")
    @Throws(StripeException::class, UserNotFoundException::class)
    fun getCustomerById(@PathVariable("customerId") customerId: String): CustomerData {
        return CustomerDataService.getCustomerById(customerId);
    }

    @GetMapping("/listCustomer")
    @Throws(StripeException::class)
    fun getCustomers(): List<CustomerData> {
        return CustomerDataService.listAllCustomers()
    }

    @PutMapping("/updateCustomer/{id}")
    @Throws(StripeException::class)
    fun update(@PathVariable("id") customerId : String,@RequestBody @Valid data: CustomerData): CustomerData {
        CustomerDataService.updateCustomer(customerId,data)
        return data
    }

    @DeleteMapping("/deleteCustomer/{id}")
    @Throws(StripeException::class)
    fun deleteCustomer(@PathVariable("id") customerId : String) : String {
        CustomerDataService.deleteCustomer(customerId)
        return "deleted successfully"
    }




}