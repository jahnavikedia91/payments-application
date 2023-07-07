package com.example.payments.service

import com.example.payments.exception.UserNotFoundException
import com.example.payments.model.CustomerData
import com.example.payments.repository.CustomerDataRepo
import com.stripe.Stripe
import com.stripe.model.Customer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CustomerDataService(private val CustomerDataRepo : CustomerDataRepo) {
    @Value("\${stripe.apikey}")
    lateinit var stripeKey: String

    fun createCustomer(data: CustomerData) {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["name"] = data.name
        params["email"] = data.email

        val customer = Customer.create(params)
        data.customerId = customer.id
        CustomerDataRepo.save(data)

    }
    fun getCustomerById(customerId: String): CustomerData {
        Stripe.apiKey = stripeKey
        try {
            val customer = Customer.retrieve(customerId)
            var cust = CustomerData()
            cust.customerId = customer.id
            cust.email = customer.email
            cust.name = customer.name
            return cust
        }
        catch(e : Exception){
            throw UserNotFoundException("No cutomer exists with id "+customerId)
        }
    }

    fun listAllCustomers(): List<CustomerData> {
        Stripe.apiKey = stripeKey
        val params: MutableMap<String, Any> = HashMap()
        params["limit"] = 3

        val customers = Customer.list(params)
        val allCustomer: MutableList<CustomerData> = ArrayList()
        for (i in customers.data.indices) {
            val customerData = CustomerData()
            customerData.customerId = customers.data[i].id
            customerData.name = customers.data[i].name
            customerData.email = customers.data[i].email
            allCustomer.add(customerData)
        }
        return allCustomer
    }

    fun updateCustomer(customerId: String, data: CustomerData) {
        Stripe.apiKey = stripeKey
        try {
            val customer = Customer.retrieve(customerId)

//        val metadata: MutableMap<String, Any> = HashMap()
//        metadata["email"] = data.email
//        metadata["name"] = data.name
            val params: MutableMap<String, Any> = HashMap()
            //params["metadata"] = metadata
            params["email"] = data.email
            params["name"] = data.name
            val updatedCustomer = customer.update(params)
            data.customerId = customerId
            CustomerDataRepo.save(data)
        }
        catch(e : Exception){
            throw UserNotFoundException("No cutomer exists with id "+customerId)
        }
    }

    fun deleteCustomer(customerId: String) {
        Stripe.apiKey = stripeKey
        try{
            val customer = Customer.retrieve(customerId)
            CustomerDataRepo.delete(getCustomerById(customerId))
            val deletedCustomer = customer.delete()
        }
        catch(e : Exception){
            throw UserNotFoundException("No cutomer exists with id "+customerId)
        }
    }


}