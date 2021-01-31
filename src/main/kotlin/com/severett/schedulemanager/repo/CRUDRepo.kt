package com.severett.schedulemanager.repo

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.util.map
import com.severett.schedulemanager.data.DBConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("BlockingMethodInNonBlockingContext")
abstract class CRUDRepo<KLASS, ID>(
    protected val tableName: String,
    protected val dbConnection: DBConnection
) {
    suspend fun getOne(id: ID): KLASS? {
        val resultFuture = dbConnection.sendPreparedStatement(
            "SELECT * FROM $tableName WHERE id = ? LIMIT 1",
            listOf(id)
        ).map { queryResult ->
            queryResult.rows.takeIf { it.isNotEmpty() }
                ?.first()
                ?.let(::instantiateInstance)
        }
        return withContext(Dispatchers.IO) { resultFuture.get() }
    }

    suspend fun getAll(): List<KLASS> {
        val resultFuture = dbConnection.sendQuery("SELECT * FROM $tableName")
            .map { queryResult ->
                queryResult.rows.map(::instantiateInstance)
            }
        return withContext(Dispatchers.IO) { resultFuture.get() }
    }

    protected abstract fun instantiateInstance(rowData: RowData): KLASS
}
