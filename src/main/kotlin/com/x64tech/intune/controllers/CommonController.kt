package com.x64tech.intune.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/common")
class CommonController () {
    @GetMapping("/public")
    fun publicRoute(): ResponseEntity<String> {
        return ResponseEntity.ok().body("OK PUBLIC ROUTE")
    }

    @GetMapping("/private")
    fun privateRoute(): ResponseEntity<String> {
        return ResponseEntity.ok().body("OK PRIVATE ROUTE")
    }
}