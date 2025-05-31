package com.x64tech.intune

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntuneApplication

fun main(args: Array<String>) {
	runApplication<IntuneApplication>(*args)
}
