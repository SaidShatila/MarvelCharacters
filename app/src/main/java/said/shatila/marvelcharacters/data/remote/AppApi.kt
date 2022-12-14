package said.shatila.marvelcharacters.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import said.shatila.marvelcharacters.data.remote.model.MainApiResponse

interface AppApi {

    @GET(Constants.getCharacters)
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<CharactersResponse>

    @GET(Constants.characterComics)
    suspend fun getCharacterComics(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<MainApiResponse>

    @GET(Constants.characterEvents)
    suspend fun getCharacterEvents(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<List<EventsDetailsResponse>>

    @GET(Constants.characterSeries)
    suspend fun getCharacterSeries(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<List<SeriesDetailsResponse>>

    @GET(Constants.characterStories)
    suspend fun getCharacterStories(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<List<StoriesDetailsResponse>>
}