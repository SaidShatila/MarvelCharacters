package said.shatila.marvelcharacters.data.models.remote.response

data class SeriesDetailResponse(
    val id : Int?,
    val title: String?,
    val description: String?,
    val thumbnail: ThumbnailResponse?,
    val next: PrePostEventsResponse?,
    val previous: PrePostEventsResponse?,
)