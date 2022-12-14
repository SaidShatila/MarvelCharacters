package said.shatila.marvelcharacters.data.models.remote.response

data class EventsDetailResponse(
    val id : Int?,
    val title: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: ThumbnailResponse?,
    val next: PrePostEventsResponse?,
    val previous: PrePostEventsResponse?,
)
