package said.shatila.marvelcharacters.data.remote.model

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val etag: String,
    val data: T,
)

/*{
  "code": 200,
  "status": "Ok",
  "etag": "f0fbae65eb2f8f28bdeea0a29be8749a4e67acb3",
  "data": {
    "offset": 0,
    "limit": 20,
    "total": 30920,
    "count": 20,
    "results": [{array of objects}}]
  }
}*/
