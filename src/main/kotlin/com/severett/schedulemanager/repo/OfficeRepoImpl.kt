package com.severett.schedulemanager.repo

import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.Office
import org.springframework.stereotype.Component

@Component
class OfficeRepoImpl(private val dbConnection: DBConnection) : OfficeRepo {
    override fun get(id: Int): Office? {
        val rows = dbConnection.sendPreparedStatement(
            "SELECT * FROM office WHERE id = ? LIMIT 1",
            listOf(id)
        ).join().rows
        return if (rows.isNotEmpty()) {
            val rowData = rows.first()
            Office(
                id = rowData.getAs("id"),
                name = rowData.getAs("name"),
                roomAmount = rowData.getAs("room_amount")
            )
        } else {
            null
        }
    }
}
