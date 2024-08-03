package com.example.courseskoinapp.data.model

data class Release(
    val contact: Contact,
    val date: Date,
    val description: String,
    val disclaimerURL: String,
    val downloadURL: String,
    val homepageURL: String,
    val laborHours: Int,
    val languages: List<String>,
    val name: String,
    val organization: String,
    val partners: List<Any>,
    val permissions: Permissions,
    val relatedCode: List<RelatedCode>,
    val repositoryURL: String,
    val status: String,
    val tags: List<String>,
    val vcs: String
)