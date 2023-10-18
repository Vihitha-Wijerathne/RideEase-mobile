package com.example.rideease.Modals


data class AddCardModal(
    var cardId: String? = null,
    var userId: String? = null,
    var name:String? = null,
    var cardNumber: String? = null,
    var expirationYear: String? = null,
    var expirationMonth: String? = null,
    var cvv: String? = null
)


