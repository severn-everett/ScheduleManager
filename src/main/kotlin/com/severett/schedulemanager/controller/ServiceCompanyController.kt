package com.severett.schedulemanager.controller

import com.severett.schedulemanager.model.ServiceCompany
import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import com.severett.schedulemanager.repo.ServiceCompanyRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/serviceCompany")
class ServiceCompanyController(private val serviceCompanyRepo: ServiceCompanyRepo) {
    @GetMapping
    suspend fun getAll(): List<ServiceCompany> = serviceCompanyRepo.getAll()

    @GetMapping("/{id}")
    suspend fun getOne(@PathVariable id: Int): ServiceCompany {
        return serviceCompanyRepo.getOne(id)
            ?: throw ResourceNotFoundException("Service Company #$id not found")
    }
}
