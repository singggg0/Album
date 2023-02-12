package com.project.sws.album.repo

import com.project.sws.album.network.ApiServices
import com.project.sws.album.network.models.SearchAlbumResult
import com.project.sws.album.network.models.SearchResult
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resumeWithException


class SearchRepoImpl @Inject constructor(private val apiServices: ApiServices) :
    SearchRepo {
    override suspend fun searchAlbum(term: String): Result<List<SearchAlbumResult>> {
        return try {
            val result = suspendCancellableCoroutine { continuation ->
                val call = apiServices.searchAlbum(term)
                val callback = object : Callback<SearchResult<SearchAlbumResult>> {
                    override fun onResponse(call: Call<SearchResult<SearchAlbumResult>>, response: Response<SearchResult<SearchAlbumResult>>) {
                        response.body()?.let {
                            if (continuation.isActive) {
                                continuation.resumeWith(Result.success(it.results))
                            }
                        } ?: run {
                            onFailure(call, Throwable("null body"))
                        }
                    }

                    override fun onFailure(call: Call<SearchResult<SearchAlbumResult>>, t: Throwable) {
                        if (continuation.isActive) {
                            continuation.resumeWithException(Exception(t.message))
                        }
                    }
                }
                call.enqueue(callback)
                continuation.invokeOnCancellation { call.cancel() }
            }
            Result.success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}