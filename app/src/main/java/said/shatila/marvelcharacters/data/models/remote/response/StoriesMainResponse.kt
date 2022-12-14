package said.shatila.marvelcharacters.data.models.remote.response

data class StoriesMainResponse(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<StoriesDetailResponse>
)
