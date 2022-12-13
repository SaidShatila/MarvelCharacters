package said.shatila.marvelcharacters.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.data.models.remote.response.CharactersResponse
import said.shatila.marvelcharacters.data.remote.model.BaseResponse

interface AppApi {

    @GET(Constants.getCharacters)
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 3,
    ): BaseResponse<CharactersResponse>

}