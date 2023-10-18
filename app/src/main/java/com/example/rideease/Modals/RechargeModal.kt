package com.example.rideease.Modals

data class RechargeModal(
    var amount: Double = 0.0,
    var date: String = ""
) {
    // Add a no-argument constructor
    constructor() : this(0.0, "")
}



