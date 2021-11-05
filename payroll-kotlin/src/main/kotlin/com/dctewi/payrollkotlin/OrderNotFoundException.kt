package com.dctewi.payrollkotlin

class OrderNotFoundException constructor(id: Long?)
    : RuntimeException("Could not find order $id")