package com.severett.schedulemanager.repo

import com.severett.schedulemanager.model.Office

interface OfficeRepo {
    fun get(id: Int): Office?
}