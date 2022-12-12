package said.shatila.marvelcharacters.data.remote

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import said.shatila.marvelcharacters.R
import said.shatila.marvelcharacters.data.remote.model.BaseResponse
import said.shatila.marvelcharacters.data.remote.model.Resource
import java.io.IOException

@Suppress("BlockingMethodInNonBlockingContext")
class ApiFlowWrapper<T> {
    operator fun invoke(apiCallBack: suspend () -> BaseResponse<T>): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Loading())
                val resultObject: BaseResponse<T> = apiCallBack()
                Log.wtf("ApiWrapper ---->", resultObject.data.toString())
                emit(Resource.Success(resultObject))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    val httpCode = e.response()!!.code()
                    val message = errorBody.byteString().utf8()
                    Log.wtf("ApiWrapper ---> $httpCode", message)
                    emit(Resource.Error(stringResource = R.string.something_went_wrong))

                } else {
                    emit(Resource.Error(stringResource = R.string.something_went_wrong))
                }
            } catch (e: IOException) {
                emit(Resource.Error(stringResource = R.string.check_server))
            } catch (unknown: Exception) {
                if (unknown.message != null) {
                    emit(Resource.Error(message = unknown.message!!))
                } else {
                    emit(Resource.Error(stringResource = R.string.something_went_wrong))
                }
            }
        }
    }

}