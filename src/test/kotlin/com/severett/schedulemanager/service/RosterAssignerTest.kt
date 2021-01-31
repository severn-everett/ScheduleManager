package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.Office
import com.severett.schedulemanager.model.ServiceCompany
import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import com.severett.schedulemanager.repo.OfficeRepo
import com.severett.schedulemanager.repo.ServiceCompanyRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Instant
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RosterAssignerTest {

    private val rosterAssigner: RosterAssigner
    private val office = Office(
        id = 1,
        name = "Office One",
        roomAmount = 21
    )
    private val serviceCompany = ServiceCompany(
        id = 1,
        name = "Service One",
        seniorCapacity = 8,
        juniorCapacity = 6
    )

    init {
        val officeRepo = mockk<OfficeRepo>()
        coEvery { officeRepo.getOne(any()) } returns null
        coEvery { officeRepo.getOne(office.id) } returns office
        val serviceCompanyRepo = mockk<ServiceCompanyRepo>()
        coEvery { serviceCompanyRepo.getOne(any()) } returns null
        coEvery { serviceCompanyRepo.getOne(serviceCompany.id) } returns serviceCompany
        rosterAssigner = RosterAssigner(officeRepo, serviceCompanyRepo)
    }

    @Test
    fun `should generate a roster`() {
        val startTime = Instant.now()
        val assignment = assertDoesNotThrow {
            runBlocking { rosterAssigner.assignRoster(office.id, serviceCompany.id, startTime) }
        }
        assertAll(
            { assertEquals(office, assignment.office) },
            { assertEquals(serviceCompany.name, assignment.serviceCompanyName) },
            { assertEquals(startTime, assignment.startTime) },
            { assertEquals(2, assignment.seniorAmt) },
            { assertEquals(1, assignment.juniorAmt) }
        )
    }

    @ParameterizedTest
    @MethodSource("generateBadParams")
    fun `should not generate a roster if ids for non-existing companies are passed in`(
        officeId: Int,
        serviceCompanyId: Int,
        expectedMessage: String
    ) {
        val thrownException = assertThrows(ResourceNotFoundException::class.java) {
            runBlocking { rosterAssigner.assignRoster(officeId, serviceCompanyId, Instant.now()) }
        }
        assertEquals(expectedMessage, thrownException.message)
    }

    @Suppress("unused")
    private fun generateBadParams(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(
                office.id + 1,
                serviceCompany.id,
                "Office #${office.id + 1} not found"
            ),
            Arguments.of(
                office.id,
                serviceCompany.id + 1,
                "Service Company #${serviceCompany.id + 1} not found"
            )
        )
    }
}
