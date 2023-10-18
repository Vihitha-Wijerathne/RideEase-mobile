package com.example.rideease.Modals


data class AddCardModal(
    val userId: String? = null,
    val name:String? = null,
    val cardNumber: String? = null,
    val expirationYear: String? = null,
    val expirationMonth: String? = null,
    val cvv: String? = null
)


