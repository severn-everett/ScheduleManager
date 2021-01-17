package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.BuildingAssignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class BuildingAssignerImpl : BuildingAssigner {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun assignRosters(
        roomsList: List<Int>,
        juniorCapacity: Int,
        seniorCapacity: Int
    ): List<BuildingAssignment> {
        return roomsList.asSequence()
            .map { roomAmt ->
                coroutineScope.async {
                    determineRoster(roomAmt, juniorCapacity, seniorCapacity)
                }
            }.map {
                runBlocking(coroutineScope.coroutineContext) { it.await() }
            }.toList()
    }

    private fun determineRoster(roomAmt: Int, juniorCapacity: Int, seniorCapacity: Int): BuildingAssignment {
        // We're looking to be just under full capacity for the building crew, so set
        // the room amount target to one above the actual room amount
        val calcRoomAmt = roomAmt + 1

        // Determine the crew capacity if we are only using senior crewmembers
        val onlySeniorAmt = ceil(calcRoomAmt.toDouble() / seniorCapacity).toInt()
        var finalSeniorAmt = onlySeniorAmt
        var finalJuniorAmt = 0
        var bestCrewCapacity = onlySeniorAmt * seniorCapacity

        // Increment downwards from the all-senior crew to see if there's a crew assignment
        // mix available consisting of both seniors and juniors that provides a more optimal
        // crew capacity. If bestCrewCapacity hits roomAmt, then we're at ideal crew capacity
        // and do not need to go any further.
        var seniorAmt = onlySeniorAmt - 1
        while (seniorAmt > 0 && bestCrewCapacity > calcRoomAmt) {
            val seniorGivenCapacity = seniorAmt * seniorCapacity
            val juniorReqCapacity = calcRoomAmt - seniorGivenCapacity
            val juniorAmt = ceil(juniorReqCapacity.toDouble() / juniorCapacity).toInt()
            val juniorGivenCapacity = juniorAmt * juniorCapacity
            val crewCapacity = seniorGivenCapacity + juniorGivenCapacity
            if (bestCrewCapacity > crewCapacity) {
                bestCrewCapacity = crewCapacity
                finalSeniorAmt = seniorAmt
                finalJuniorAmt = juniorAmt
            }
            seniorAmt -= 1
        }
        return BuildingAssignment(finalJuniorAmt, finalSeniorAmt)
    }
}
