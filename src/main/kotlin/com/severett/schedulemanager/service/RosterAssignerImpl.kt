package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.Assignment
import com.severett.schedulemanager.model.Office
import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import com.severett.schedulemanager.repo.OfficeRepo
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.math.ceil

@Service
class RosterAssignerImpl(private val officeRepo: OfficeRepo) : RosterAssigner {

    override fun assignRoster(
        officeId: Int,
        startTime: Instant,
        seniorCapacity: Int,
        juniorCapacity: Int
    ): Assignment {
        val office = officeRepo.get(officeId)
            ?: throw ResourceNotFoundException("Office #$officeId not found")
        return determineRoster(office, startTime, seniorCapacity, juniorCapacity)
    }

    private fun determineRoster(
        office: Office,
        startTime: Instant,
        seniorCapacity: Int,
        juniorCapacity: Int
    ): Assignment {
        // We're looking to be just under full capacity for the building crew, so set
        // the room amount target to one above the actual room amount
        val roomAmt = office.roomAmount + 1

        // Determine the crew capacity if we are only using senior crewmembers
        val onlySeniorAmt = ceil(roomAmt.toDouble() / seniorCapacity).toInt()
        var finalSeniorAmt = onlySeniorAmt
        var finalJuniorAmt = 0
        var bestCrewCapacity = onlySeniorAmt * seniorCapacity

        // Increment downwards from the all-senior crew to see if there's a crew assignment
        // mix available consisting of both seniors and juniors that provides a more optimal
        // crew capacity. If bestCrewCapacity hits roomAmt, then we're at ideal crew capacity
        // and do not need to go any further.
        var seniorAmt = onlySeniorAmt - 1
        while (seniorAmt > 0 && bestCrewCapacity > roomAmt) {
            val seniorGivenCapacity = seniorAmt * seniorCapacity
            val juniorReqCapacity = roomAmt - seniorGivenCapacity
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
        return Assignment(
            office = office,
            startTime = startTime,
            seniorAmt = finalSeniorAmt,
            juniorAmt = finalJuniorAmt
        )
    }
}
