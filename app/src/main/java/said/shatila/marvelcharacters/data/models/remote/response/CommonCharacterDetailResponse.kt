package said.shatila.marvelcharacters.data.models.remote.response

data class CommonCharacterDetailResponse(
    val id: Int?,
    val title: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: ThumbnailResponse?,
    val next: PrePostEventsResponse?,
    val prices: List<PriceResponse>?,
    val previous: PrePostEventsResponse?,
    val type: String?,
)