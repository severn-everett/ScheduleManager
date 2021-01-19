package com.severett.schedulemanager.repo

import com.github.jasync.sql.db.util.map
import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.Office
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class OfficeRepoImpl(private val dbConnection: DBConnection) : OfficeRepo {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun get(id: Int): Office? {
        val resultFuture = dbConnection.sendPreparedStatement(
            "SELECT * FROM office WHERE id = ? LIMIT 1",
            listOf(id)
        ).map { queryResult ->
            queryResult.rows.takeIf { resultSet -> resultSet.isNotEmpty() }?.let { resultSet ->
                val rowData = resultSet.first()
                Office(
                    id = rowData.getAs("id"),
                    name = rowData.getAs("name"),
                    roomAmount = rowData.getAs("room_amount")
                )
            }
        }
        return withContext(Dispatchers.IO) { resultFuture.get() }
    }
}
