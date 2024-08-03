package com.example.courseskoinapp.data.model

data class Permissions(
    val exemptionText: Any,
    val licenses: List<License>,
    val usageType: String
)