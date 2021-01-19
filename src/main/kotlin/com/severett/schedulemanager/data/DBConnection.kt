package com.severett.schedulemanager.data

import com.github.jasync.sql.db.QueryResult
import java.util.concurrent.CompletableFuture

interface DBConnection {
    fun sendQuery(query: String): CompletableFuture<QueryResult>
    fun sendPreparedStatement(query: String, values: List<Any?> = emptyList()): CompletableFuture<QueryResult>
}
