package said.shatila.marvelcharacters.data.remote

object Constants {

    const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    const val getCharacters = "characters"
    const val character = "characters/{characterId}"
    const val characterComics = "characters/{characterId}/comics"
    const val characterEvents = "characters/{characterId}/events"
    const val characterSeries = "characters/{characterId}/series"
    const val characterStories = "characters/{characterId}/stories"

    const val TS = "ts"
    const val HASH = "hash"
    const val APIKEY = "apikey"
    const val PUBLIC_KEY = "9efef0156cf933d33cb4dfac8c66ac06"
    const val PRIVATE_KEY = "bbaa390ef407293c5224d2a4afa39c9f278e104d"

}