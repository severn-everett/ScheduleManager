package com.severett.schedulemanager.repo

import com.severett.schedulemanager.model.Office
import org.springframework.stereotype.Component

@Component
class OfficeRepoImpl : OfficeRepo {
    private val repo = mapOf(
        1 to Office(1, "Office One", 35),
        2 to Office(2, "Office Two", 21),
        3 to Office(3, "Office Three", 17),
        4 to Office(4, "Office Four", 24),
        5 to Office(5, "Office Five", 28),
    )

    override fun get(id: Int): Office? {
        return repo[id]
    }
}
