package com.androiddevs.ktornoteapp.data.remote.requests

data class AddOwnerRequest(
    val ownersEmail: String,
    val noteId: String
)
