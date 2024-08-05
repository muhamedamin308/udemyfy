package com.example.courseskoinapp.data.model

data class Contact(
    val email: String? = null, // Make nullable if it can be null
    val phone: String? = null  // Make nullable if it can be null
) {
    override fun hashCode(): Int {
        var result = email?.hashCode() ?: 0
        result = 31 * result + (phone?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (email != other.email) return false
        if (phone != other.phone) return false

        return true
    }
}
