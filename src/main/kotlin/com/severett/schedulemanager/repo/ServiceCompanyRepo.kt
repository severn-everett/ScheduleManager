package com.severett.schedulemanager.repo

import com.severett.schedulemanager.model.ServiceCompany

interface ServiceCompanyRepo {
    fun get(id: Int): ServiceCompany?
}
