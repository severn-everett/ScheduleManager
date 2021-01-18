package com.severett.schedulemanager.model

import com.severett.schedulemanager.data.serializer.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Assignment(
    val office: Office,
    val serviceCompanyName: String,
    @Serializable(with = InstantSerializer::class)
    val startTime: Instant,
    val seniorAmt: Int,
    val juniorAmt: Int
) {
    init {
        require(seniorAmt >= 0) { "Amount of senior workers must not be negative" }
        require(juniorAmt >= 0) { "Amount of junior workers must not be negative" }
    }
}
