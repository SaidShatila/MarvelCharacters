package said.shatila.marvelcharacters.data.remote.repository

import said.shatila.marvelcharacters.data.models.remote.response.CharactersResponse
import said.shatila.marvelcharacters.data.remote.AppApi
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import javax.inject.Inject

class MainRepository @Inject constructor(private val appApi: AppApi) {

    fun getCharacters(): BaseResponse<CharactersResponse> {
        return appApi.getCharacters()
    }

}
