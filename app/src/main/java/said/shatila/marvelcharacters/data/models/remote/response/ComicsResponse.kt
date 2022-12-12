package said.shatila.marvelcharacters.data.models.remote.response

data class ComicsResponse(
    val available: Int,
    val collectionURI: String,
    val items: List<CommonDetailsResponse>,
    val returned: Int
)

/*
"resourceURI":"http://gateway.marvel.com/v1/public/comics/10225",
                     "name":"Marvel Premiere (1972) #37"
                  }*/