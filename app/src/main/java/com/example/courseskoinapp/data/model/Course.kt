package com.example.courseskoinapp.data.model


data class Course(
    val agency: String,
    val measurementType: MeasurementType,
    val releases: List<Release>,
    val version: String
)