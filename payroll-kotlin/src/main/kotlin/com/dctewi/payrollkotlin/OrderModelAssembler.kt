package com.dctewi.payrollkotlin

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class OrderModelAssembler: RepresentationModelAssembler<Order, EntityModel<Order>> {
    override fun toModel(entity: Order): EntityModel<Order> {
        val orderModel = EntityModel.of(entity,
                linkTo<OrderController> { one(entity.id) }.withSelfRel(),
                linkTo<OrderController> { all() }.withRel("orders"))

        if (entity.status == Status.IN_PROGRESS) {
            orderModel.add(linkTo<OrderController> { cancel(entity.id) }.withRel("cancel"))
            orderModel.add(linkTo<OrderController> { complete(entity.id) }.withRel("complete"))
        }

        return orderModel
    }
}