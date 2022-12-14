package said.shatila.marvelcharacters.data.models.remote.response

data class ComicMainResponse(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<ComicDetailsResponse>
)
