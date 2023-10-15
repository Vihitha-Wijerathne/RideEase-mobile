package com.example.rideease.Modals

data class RouteModel(
    val routeId: String, // ID for the route
    val source: String, // Source location of the route
    val destination: String, // Destination location of the route
    val fare: Double // Fare for the route
){
    override fun toString(): String {
        return routeId
    }
}
