package com.severett.schedulemanager.controller

import com.severett.schedulemanager.model.Office
import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import com.severett.schedulemanager.repo.OfficeRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/office")
class OfficeController(private val officeRepo: OfficeRepo) {
    @GetMapping
    suspend fun getAll(): List<Office> = officeRepo.getAll()

    @GetMapping("/{id}")
    suspend fun getOffice(@PathVariable id: Int): Office {
        return officeRepo.getOne(id) ?: throw ResourceNotFoundException("Office #$id not found")
    }
}
