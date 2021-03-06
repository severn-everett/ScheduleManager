package com.severett.schedulemanager.controller

import com.severett.schedulemanager.model.Assignment
import com.severett.schedulemanager.service.RosterAssigner
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/assignment")
class AssignmentController(
    private val rosterAssigner: RosterAssigner
) {

    @GetMapping
    @ResponseBody
    suspend fun getAssignment(
        @RequestParam officeId: Int,
        @RequestParam serviceCompanyId: Int
    ): Assignment {
        return rosterAssigner.assignRoster(
            officeId = officeId,
            serviceCompanyId = serviceCompanyId,
            startTime = Instant.now()
        )
    }
}
