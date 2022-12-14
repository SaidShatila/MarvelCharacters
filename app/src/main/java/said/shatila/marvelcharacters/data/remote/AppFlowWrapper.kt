package said.shatila.marvelcharacters.data.remote

import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import said.shatila.marvelcharacters.util.NetworkResult

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponse<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.code == 200) {
                val body = response.data
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code} ${response.status}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}