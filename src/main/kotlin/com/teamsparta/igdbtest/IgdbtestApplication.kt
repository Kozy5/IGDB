package com.teamsparta.igdbtest

import jakarta.persistence.Entity
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class IgdbtestApplication

fun main(args: Array<String>) {
    runApplication<IgdbtestApplication>(*args)
}
