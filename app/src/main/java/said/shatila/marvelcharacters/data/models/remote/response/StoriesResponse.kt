package said.shatila.marvelcharacters.data.models.remote.response

data class StoriesResponse(
    val title: String?,
    val description: String?,
    val thumbnail: ThumbnailResponse?,
    val type: String?,
)
