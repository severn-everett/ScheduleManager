package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.Assignment
import java.time.Instant

interface RosterAssigner {
    fun assignRoster(
        officeId: Int,
        serviceCompanyId: Int,
        startTime: Instant
    ): Assignment
}
