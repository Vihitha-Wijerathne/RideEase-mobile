class RouteModel {
    var routeId: String? = null
    var source: String? = null
    var destination: String? = null
    var fare: Double? = null

    constructor()

    constructor(routeId: String, source: String, destination: String, fare: Double) {
        this.routeId = routeId
        this.source = source
        this.destination = destination
        this.fare = fare
    }
}
