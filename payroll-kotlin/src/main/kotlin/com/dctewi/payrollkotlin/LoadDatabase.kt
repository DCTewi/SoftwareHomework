package com.dctewi.payrollkotlin

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class LoadDatabase {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun initDatabase(employeeRepository: EmployeeRepository, orderRepository: OrderRepository): CommandLineRunner {
        return CommandLineRunner {
            employeeRepository.save(Employee("Bilbo", "Dave", "burglar"))
            employeeRepository.save(Employee("Frodo", "Dave", "thief"))
            employeeRepository.findAll().forEach(Consumer { employee: Employee -> log.info("Preloaded $employee") })
            orderRepository.save(Order("MacBook Pro", Status.COMPLETED))
            orderRepository.save(Order("iPhone", Status.IN_PROGRESS))
            orderRepository.findAll().forEach(Consumer { order: Order -> log.info("Preloaded $order") })
        }
    }
}
