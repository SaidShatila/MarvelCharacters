package said.shatila.marvelcharacters.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.data.remote.model.BaseResponse

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
    ): BaseResponse<CommonMainCharacterDetailResponse>

    @GET(Constants.characterEvents)
    suspend fun getCharacterEvents(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<CommonMainCharacterDetailResponse>

    @GET(Constants.characterSeries)
    suspend fun getCharacterSeries(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<CommonMainCharacterDetailResponse>

    @GET(Constants.characterStories)
    suspend fun getCharacterStories(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<CommonMainCharacterDetailResponse>
}