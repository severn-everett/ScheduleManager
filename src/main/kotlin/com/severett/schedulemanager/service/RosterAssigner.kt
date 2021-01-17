package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.Assignment
import java.time.Instant

interface RosterAssigner {
    fun assignRoster(
        officeId: Int,
        startTime: Instant,
        seniorCapacity: Int,
        juniorCapacity: Int
    ): Assignment
}
