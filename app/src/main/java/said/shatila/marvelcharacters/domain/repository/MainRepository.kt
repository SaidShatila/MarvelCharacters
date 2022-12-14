package said.shatila.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.data.remote.AppApiImplementation
import said.shatila.marvelcharacters.data.remote.BaseApiResponse
import said.shatila.marvelcharacters.data.remote.paging.CharactersPagingSource
import said.shatila.marvelcharacters.util.NetworkResult
import javax.inject.Inject


class MainRepository @Inject constructor(private val appApiImplementation: AppApiImplementation) :
    BaseApiResponse() {


    fun characters(): Flow<PagingData<CharacterResponse>> {
        return Pager(config = PagingConfig(pageSize = pageSize, maxSize = pageMax),
            pagingSourceFactory = { CharactersPagingSource(appApiImplementation) }).flow
    }

    companion object {
        const val pageSize: Int = 20
        const val pageMax: Int = 200
    }

    suspend fun getCharacterComics(characterId: Int): Flow<NetworkResult<ComicMainResponse>> =
        flow {
            emit(safeApiCall {
                appApiImplementation.getCharacterComics(characterId, 3)
            })
        }.flowOn(Dispatchers.IO)

    suspend fun getCharacterEvents(characterId: Int): Flow<NetworkResult<EventsMainResponse>> =
        flow {
            emit(safeApiCall {
                appApiImplementation.getCharacterEvents(characterId, 3)
            })
        }.flowOn(Dispatchers.IO)

    suspend fun getCharacterSeries(characterId: Int): Flow<NetworkResult<SeriesMainResponse>> =
        flow {
            emit(safeApiCall {
                appApiImplementation.getCharacterSeries(characterId, 3)
            })
        }.flowOn(Dispatchers.IO)

    suspend fun getCharacterStories(characterId: Int): Flow<NetworkResult<StoriesMainResponse>> =
        flow {
            emit(safeApiCall {
                appApiImplementation.getCharacterStories(characterId, 3)
            })
        }.flowOn(Dispatchers.IO)


}


