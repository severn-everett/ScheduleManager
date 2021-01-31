package com.severett.schedulemanager.repo

import com.github.jasync.sql.db.RowData
import com.severett.schedulemanager.data.DBConnection
import com.severett.schedulemanager.model.ServiceCompany
import org.springframework.stereotype.Component

@Component
class ServiceCompanyRepo(
    dbConnection: DBConnection
) : CRUDRepo<ServiceCompany, Int>("service_company", dbConnection) {
    override fun instantiateInstance(rowData: RowData): ServiceCompany {
        return ServiceCompany(
            id = rowData.getAs("id"),
            name = rowData.getAs("name"),
            seniorCapacity = rowData.getAs("senior_capacity"),
            juniorCapacity = rowData.getAs("junior_capacity")
        )
    }
}
