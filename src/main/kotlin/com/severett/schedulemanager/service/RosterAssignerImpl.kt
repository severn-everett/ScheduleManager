package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.Assignment
import com.severett.schedulemanager.model.Office
import com.severett.schedulemanager.model.ServiceCompany
import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import com.severett.schedulemanager.repo.OfficeRepo
import com.severett.schedulemanager.repo.ServiceCompanyRepo
import kotlinx.coroutines.yield
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.math.ceil

@Service
class RosterAssignerImpl(
    private val officeRepo: OfficeRepo,
    private val serviceCompanyRepo: ServiceCompanyRepo
) : RosterAssigner {

    override suspend fun assignRoster(
        officeId: Int,
        serviceCompanyId: Int,
        startTime: Instant
    ): Assignment {
        val office = officeRepo.get(officeId)
            ?: throw ResourceNotFoundException("Office #$officeId not found")
        val serviceCompany = serviceCompanyRepo.get(serviceCompanyId)
            ?: throw ResourceNotFoundException("Service Company #$serviceCompanyId not found")
        return determineRoster(office, serviceCompany, startTime)
    }

    private suspend fun determineRoster(
        office: Office,
        serviceCompany: ServiceCompany,
        startTime: Instant
    ): Assignment {
        // We're looking to be just under full capacity for the building crew, so set
        // the room amount target to one above the actual room amount
        val roomAmt = office.roomAmount + 1

        // Determine the crew capacity if we are only using senior crewmembers
        val onlySeniorAmt = ceil(roomAmt.toDouble() / serviceCompany.seniorCapacity).toInt()
        var finalSeniorAmt = onlySeniorAmt
        var finalJuniorAmt = 0
        var bestCrewCapacity = onlySeniorAmt * serviceCompany.seniorCapacity

        // Increment downwards from the all-senior crew to see if there's a crew assignment
        // mix available consisting of both seniors and juniors that provides a more optimal
        // crew capacity. If bestCrewCapacity hits roomAmt, then we're at ideal crew capacity
        // and do not need to go any further.
        var seniorAmt = onlySeniorAmt - 1
        while (seniorAmt > 0 && bestCrewCapacity > roomAmt) {
            val seniorGivenCapacity = seniorAmt * serviceCompany.seniorCapacity
            val juniorReqCapacity = roomAmt - seniorGivenCapacity
            val juniorAmt = ceil(juniorReqCapacity.toDouble() / serviceCompany.juniorCapacity).toInt()
            val juniorGivenCapacity = juniorAmt * serviceCompany.juniorCapacity
            val crewCapacity = seniorGivenCapacity + juniorGivenCapacity
            if (bestCrewCapacity > crewCapacity) {
                bestCrewCapacity = crewCapacity
                finalSeniorAmt = seniorAmt
                finalJuniorAmt = juniorAmt
            }
            seniorAmt -= 1
            yield()
        }
        return Assignment(
            office = office,
            serviceCompanyName = serviceCompany.name,
            startTime = startTime,
            seniorAmt = finalSeniorAmt,
            juniorAmt = finalJuniorAmt
        )
    }
}
