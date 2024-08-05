package com.example.courseskoinapp.data.model

data class Permissions(
    val exemptionText: String? = null,
    val licenses: List<License> = emptyList(),
    val usageType: String = ""
) {
    override fun hashCode(): Int {
        var result = exemptionText?.hashCode() ?: 0
        result = 31 * result + licenses.hashCode()
        result = 31 * result + usageType.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Permissions

        if (exemptionText != other.exemptionText) return false
        if (licenses != other.licenses) return false
        if (usageType != other.usageType) return false

        return true
    }
}
