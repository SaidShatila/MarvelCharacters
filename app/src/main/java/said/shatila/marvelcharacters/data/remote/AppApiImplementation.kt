package said.shatila.marvelcharacters.data.remote

import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import javax.inject.Inject

class AppApiImplementation @Inject constructor(private val appApi: AppApi) : AppApi {

    override suspend fun getCharacters(offset: Int, limit: Int): BaseResponse<CharactersResponse> =
        appApi.getCharacters(offset, limit)

    override suspend fun getCharacterComics(
        characterId: Int,
        limit: Int
    ): BaseResponse<CommonMainCharacterDetailResponse> {
        return appApi.getCharacterComics(characterId, limit)
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        limit: Int
    ): BaseResponse<CommonMainCharacterDetailResponse> {
        return appApi.getCharacterEvents(characterId, limit)
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        limit: Int
    ): BaseResponse<CommonMainCharacterDetailResponse> {
        return appApi.getCharacterSeries(characterId, limit)
    }

    override suspend fun getCharacterStories(
        characterId: Int,
        limit: Int
    ): BaseResponse<CommonMainCharacterDetailResponse> {
        return appApi.getCharacterStories(characterId, limit)
    }
}
