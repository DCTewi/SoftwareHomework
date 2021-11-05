package com.dctewi.payrollkotlin

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "CUSTOMER_ORDER")
class Order constructor() {

    @Id @GeneratedValue
    var id: Long = 0
    var description = ""
    var status = Status.IN_PROGRESS

    constructor(description: String, status: Status): this() {
        this.description = description
        this.status = status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false

        val order: Order = other
        return Objects.equals(id, order.id) &&
                Objects.equals(description, order.description) &&
                Objects.equals(status, order.status)
    }

    override fun hashCode() = Objects.hash(id, description, status)

    override fun toString() = "Order{ id=$id, description=$description, status=$status }"
}