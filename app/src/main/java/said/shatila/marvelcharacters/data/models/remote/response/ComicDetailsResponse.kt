package said.shatila.marvelcharacters.data.models.remote.response

data class ComicDetailsResponse(
    val id: Int?,
    val title: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: ThumbnailResponse?,
    val prices: List<PriceResponse>?,
    val resourceURI: String?,
    val images: List<ThumbnailResponse>?,
    val characters: CharactersResponse?,
)
