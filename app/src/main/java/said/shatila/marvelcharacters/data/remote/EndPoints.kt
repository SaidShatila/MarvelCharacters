package said.shatila.marvelcharacters.data.remote

object EndPoints {
    const val baseUrl =
        "https://gateway.marvel.com/v1/public/?apikey=48fd7507684981970e9bc356f1837021"

    const val getCharacters = "characters"

    const val character = "characters/{characterId}"
    const val characterComics = "characters/{characterId}/comics"
    const val characterEvents = "characters/{characterId}/events"
    const val characterSeries = "characters/{characterId}/series"
    const val characterStories = "characters/{characterId}/stories"


}