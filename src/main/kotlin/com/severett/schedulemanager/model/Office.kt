package com.severett.schedulemanager.model

import kotlinx.serialization.Serializable

@Serializable
@JvmRecord
data class Office(val id: Int, val name: String, val roomAmount: Int)
