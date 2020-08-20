package taxipark

import java.lang.Integer.min

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers - trips.map { it.driver }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { p -> trips.count { p in it.passengers } >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.count { it.driver == driver && passenger in it.passengers } > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { passenger ->
            val withDiscount = trips.count { passenger in it.passengers && it.discount != null }
            val withoutDiscount = trips.count { passenger in it.passengers && it.discount == null }
            withDiscount > withoutDiscount
        }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
        trips.groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end
        }.maxBy { it.value.count() }?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean
{
    val allCost = trips.sumByDouble { it.cost }
    val costList = trips.groupBy { it.driver }
            .map { (_, driverTrips) ->
                driverTrips.sumByDouble { it.cost }
            }.sortedDescending()
    var cullCost = costList.take(allDrivers.size / 5).sum()
    return trips.isNotEmpty() && cullCost >= allCost * 0.8
}