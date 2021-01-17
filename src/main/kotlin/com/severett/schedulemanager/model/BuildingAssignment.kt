package com.severett.schedulemanager.model

import kotlinx.serialization.Serializable

@Serializable
data class BuildingAssignment(val junior: Int, val senior: Int) {
    init {
        require(junior >= 0) { "Amount of junior workers must not be negative" }
        require(senior >= 0) { "Amount of senior workers must not be negative" }
    }
}
