package said.shatila.marvelcharacters.data.remote.model

import said.shatila.marvelcharacters.data.models.remote.response.ComicDetailsResponse

data class MainApiResponse(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<ComicDetailsResponse>
)
