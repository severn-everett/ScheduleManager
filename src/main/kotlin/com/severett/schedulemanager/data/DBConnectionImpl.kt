package com.severett.schedulemanager.data

import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import javax.annotation.PreDestroy

@Service
class DBConnectionImpl : DBConnection {
    private val connection = PostgreSQLConnectionBuilder.createConnectionPool(
        "jdbc:postgresql://localhost:5432/schedule_management?user=db_user&password=db_password"
    )

    @PreDestroy
    fun preDestroy() {
        logger.info { "Shutting down database connection..." }
        connection.disconnect().join()
        logger.info { "Database connection shut down" }
    }

    override fun sendQuery(query: String): CompletableFuture<QueryResult> = connection.sendQuery(query)

    override fun sendPreparedStatement(query: String, values: List<Any?>): CompletableFuture<QueryResult> {
        return connection.sendPreparedStatement(query, values)
    }

    private companion object : KLogging()
}
