package said.shatila.marvelcharacters.data.remote.model

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val etag: String,
    val data: T,
)
