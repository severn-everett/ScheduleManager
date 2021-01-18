package com.severett.schedulemanager.repo

import com.severett.schedulemanager.model.ServiceCompany
import org.springframework.stereotype.Component

@Component
class ServiceCompanyRepoImpl : ServiceCompanyRepo {
    private val repo = mapOf(
        1 to ServiceCompany(1, "Service Company One", 10, 6),
        2 to ServiceCompany(2, "Service Company Two", 11, 6)
    )

    override fun get(id: Int) = repo[id]
}
