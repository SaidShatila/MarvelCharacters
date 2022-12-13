package said.shatila.marvelcharacters.domain.repository

import said.shatila.marvelcharacters.data.models.remote.response.CharactersResponse
import said.shatila.marvelcharacters.data.remote.AppApiImplementation
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import javax.inject.Inject

class MainRepository @Inject constructor(private val appApiImplementation: AppApiImplementation) {

    suspend fun getCharacters(): BaseResponse<CharactersResponse> {
        return appApiImplementation.getCharacters(limit = 3)
    }

}
