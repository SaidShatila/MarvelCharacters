package said.shatila.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.data.remote.AppApiImplementation
import said.shatila.marvelcharacters.data.remote.paging.CharactersPagingSource
import javax.inject.Inject

class MainRepository @Inject constructor(private val appApiImplementation: AppApiImplementation) {


    fun characters(): Flow<PagingData<CharacterResponse>> {
        return Pager(config = PagingConfig(pageSize = pageSize, maxSize = pageMax),
            pagingSourceFactory = { CharactersPagingSource(appApiImplementation) }
        ).flow
    }

    companion object {
        const val pageSize: Int = 20
        const val pageMax: Int = 200
    }

}