package com.dctewi.payrollkotlin

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class EmployeeModelAssembler : RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    override fun toModel(entity: Employee): EntityModel<Employee> =
            EntityModel.of(entity,
                    linkTo<EmployeeController> { one(entity.id) }.withSelfRel(),
                    linkTo<EmployeeController> { all() }.withRel("employees"))
}
