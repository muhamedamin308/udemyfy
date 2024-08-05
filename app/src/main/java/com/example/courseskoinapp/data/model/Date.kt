package com.example.courseskoinapp.data.model

data class Date(
    val created: String? = null,
    val lastModified: String? = null,
    val metadataLastUpdated: String? = null
) {
    override fun hashCode(): Int {
        var result = created?.hashCode() ?: 0
        result = 31 * result + (lastModified?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Date

        if (created != other.created) return false
        if (lastModified != other.lastModified) return false
        if (metadataLastUpdated != other.metadataLastUpdated) return false

        return true
    }
}