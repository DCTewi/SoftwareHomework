package com.dctewi.payrollkotlin

class EmployeeNotFoundException constructor(id: Long?)
    : RuntimeException("Could not find employee $id")