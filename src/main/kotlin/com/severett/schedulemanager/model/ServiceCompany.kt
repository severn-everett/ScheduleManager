package com.severett.schedulemanager.model

import kotlinx.serialization.Serializable

@Serializable
@JvmRecord
data class ServiceCompany(val id: Int, val name: String, val seniorCapacity: Int, val juniorCapacity: Int)
