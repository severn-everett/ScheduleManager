package com.severett.schedulemanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Office(val id: Int, val name: String, val roomAmount: Int)
