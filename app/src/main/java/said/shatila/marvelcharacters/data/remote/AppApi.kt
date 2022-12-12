package said.shatila.marvelcharacters.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import said.shatila.marvelcharacters.data.models.remote.response.CharactersResponse
import said.shatila.marvelcharacters.data.remote.model.BaseResponse

interface AppApi {

    @GET(EndPoints.getCharacters)
    fun getCharacters(
    ): BaseResponse<CharactersResponse>

}