package com.dctewi.payrollkotlin

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/employees")
class EmployeeController constructor(private val repository: EmployeeRepository,
                                     private val assembler: EmployeeModelAssembler) {

    @GetMapping
    fun all(): CollectionModel<EntityModel<Employee>>
    {
        val employees: List<EntityModel<Employee>> = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList())

        return CollectionModel.of(employees,
                linkTo<EmployeeController> { all() }.withSelfRel())
    }

    @GetMapping("/{id}")
    fun one(@PathVariable id: Long): EntityModel<Employee> {
        val employee = repository.findById(id)
                .orElseThrow { EmployeeNotFoundException(id) }

        return assembler.toModel(employee)
    }

    @PostMapping
    fun add(@RequestBody newEmployee: Employee): ResponseEntity<EntityModel<Employee>> {
        val entityModel = assembler.toModel(repository.save(newEmployee))

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel)
    }

    @PutMapping("/{id}")
    fun replaceOne(@RequestBody newEmployee: Employee, @PathVariable id: Long): ResponseEntity<EntityModel<Employee>> {
        val updatedEmployee = repository.findById(id)
                .map(fun (employee: Employee): Employee {
                    employee.name = newEmployee.name
                    employee.role = newEmployee.role
                    return repository.save(employee)
                })
                .orElseGet(fun (): Employee {
                    newEmployee.id = id
                    return repository.save(newEmployee)
                })

        val entityModel = assembler.toModel(updatedEmployee)

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel)
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: Long): ResponseEntity<EntityModel<Employee>> {
        repository.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}