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
    const val PUBLIC_KEY = "48fd7507684981970e9bc356f1837021"
    const val PRIVATE_KEY = "c0ffe4209d72c5fcd2efe3a9bbc6e1bb0df27d81"

}