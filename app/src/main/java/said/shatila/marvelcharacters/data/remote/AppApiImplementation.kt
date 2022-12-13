package said.shatila.marvelcharacters.data.remote

import said.shatila.marvelcharacters.data.models.remote.response.CharactersResponse
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import javax.inject.Inject

class AppApiImplementation @Inject constructor(private val appApi: AppApi) : AppApi {

    override suspend fun getCharacters(offset: Int, limit: Int): BaseResponse<CharactersResponse> = appApi.getCharacters(offset,limit)

}
