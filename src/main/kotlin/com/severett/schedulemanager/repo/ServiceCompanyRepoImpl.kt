package com.severett.schedulemanager.repo

import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.ServiceCompany
import org.springframework.stereotype.Component

@Component
class ServiceCompanyRepoImpl(private val dbConnection: DBConnection) : ServiceCompanyRepo {
    override fun get(id: Int): ServiceCompany? {
        val rows = dbConnection.sendPreparedStatement(
            "SELECT * FROM service_company WHERE id = ? LIMIT 1",
            listOf(id)
        ).join().rows
        return if (rows.isNotEmpty()) {
            val rowData = rows.first()
            ServiceCompany(
                id = rowData.getAs("id"),
                name = rowData.getAs("name"),
                seniorCapacity = rowData.getAs("senior_capacity"),
                juniorCapacity = rowData.getAs("junior_capacity")
            )
        } else {
            null
        }
    }
}
