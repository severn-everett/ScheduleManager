package com.severett.schedulemanager.service

import com.severett.schedulemanager.model.BuildingAssignment

interface BuildingAssigner {
    fun assignRosters(roomsList: List<Int>, juniorCapacity: Int, seniorCapacity: Int): List<BuildingAssignment>
}
