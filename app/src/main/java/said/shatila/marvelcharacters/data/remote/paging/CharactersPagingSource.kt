package said.shatila.marvelcharacters.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.data.remote.AppApiImplementation

class CharactersPagingSource(private val apiService: AppApiImplementation) :
    PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {
        return try {
            val offset = params.key ?: 0

            val response = apiService.getCharacters(offset, 3)
            val responseOffset = response.data.offset
            val totalCharacters = response.data.total

            LoadResult.Page(
                data = response.data.results,
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}