package com.androiddevs.ktornoteapp.data.remote

import com.androiddevs.ktornoteapp.data.local.entitys.Note
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.data.remote.requests.AddOwnerRequest
import com.androiddevs.ktornoteapp.data.remote.requests.DeleteNoteRequest
import com.androiddevs.ktornoteapp.data.remote.responses.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {

    @POST(value = "/register")
    suspend fun register(
        @Body registerRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST(value = "/login")
    suspend fun login(
        @Body loginRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST(value = "/addNote")
    suspend fun addNote(
        @Body note: Note
    ): Response<ResponseBody>

    @POST(value = "/deleteNote")
    suspend fun deleteNote(
        @Body deleteNoteRequest: DeleteNoteRequest
    ): Response<ResponseBody>

    @POST(value = "/addOwnerToNote")
    suspend fun addOwnerToNote(
        @Body addOwnerRequest: AddOwnerRequest
    ): Response<SimpleResponse>

    @GET(value = "/getNotes")
    suspend fun getNotes(): Response<List<Note>>
}