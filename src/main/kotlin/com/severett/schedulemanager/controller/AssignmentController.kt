package com.severett.schedulemanager.controller

import com.severett.schedulemanager.model.BuildingAssignment
import com.severett.schedulemanager.service.BuildingAssigner
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AssignmentController(private val buildingAssigner: BuildingAssigner) {

    @GetMapping("/assignments")
    @ResponseBody
    fun getAssignments(): List<BuildingAssignment> {
        return buildingAssigner.assignRosters(
            roomsList = listOf(24, 28),
            juniorCapacity = 6,
            seniorCapacity = 11
        )
    }
}
