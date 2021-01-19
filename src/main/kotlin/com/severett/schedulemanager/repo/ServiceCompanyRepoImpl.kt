package com.severett.schedulemanager.repo

import com.github.jasync.sql.db.util.map
import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.ServiceCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ServiceCompanyRepoImpl(private val dbConnection: DBConnection) : ServiceCompanyRepo {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun get(id: Int): ServiceCompany? {
        val resultFuture = dbConnection.sendPreparedStatement(
            "SELECT * FROM service_company WHERE id = ? LIMIT 1",
            listOf(id)
        ).map { queryResult ->
            queryResult.rows.takeIf { resultSet -> resultSet.isNotEmpty() }?.let { resultSet ->
                val rowData = resultSet.first()
                ServiceCompany(
                    id = rowData.getAs("id"),
                    name = rowData.getAs("name"),
                    seniorCapacity = rowData.getAs("senior_capacity"),
                    juniorCapacity = rowData.getAs("junior_capacity")
                )
            }
        }
        return withContext(Dispatchers.IO) { resultFuture.get() }
    }
}
