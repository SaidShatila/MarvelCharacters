package said.shatila.marvelcharacters.data.models.remote.response

data class ComicsResponse(
    val available: Int,
    val collectionURI: String,
    val items: List<CommonDetailsResponse>,
    val returned: Int
)