package com.severett.schedulemanager.repo

import com.github.jasync.sql.db.RowData
import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.Office
import org.springframework.stereotype.Component

@Component
class OfficeRepo(dbConnection: DBConnection) : CRUDRepo<Office, Int>("office" , dbConnection) {
    override fun instantiateInstance(rowData: RowData): Office {
        return Office(
            id = rowData.getAs("id"),
            name = rowData.getAs("name"),
            roomAmount = rowData.getAs("room_amount")
        )
    }
}
