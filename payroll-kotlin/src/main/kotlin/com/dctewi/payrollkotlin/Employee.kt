package com.dctewi.payrollkotlin

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Employee constructor() {

    @Id @GeneratedValue
    var id: Long = 0
    var firstName = ""
    var lastName = ""
    var role = ""

    var name
        get() = "$firstName $lastName"
        set(value) {
            val parts = value.split(" ")
            if (parts.size == 2) {
                firstName = parts[0]
                lastName = parts[1]
            } else {
                firstName = value
            }
        }

    constructor(firstName: String, lastName: String, role: String) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.role = role
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        val employee: Employee = other
        return Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(role, employee.role)
    }

    override fun hashCode() = Objects.hash(id, name, role)

    override fun toString() = "Employee{ id=$id, name='$name', role='$role' }"
}
