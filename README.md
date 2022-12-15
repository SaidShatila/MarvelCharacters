# Marvel Characters
## This sample was created to showcase my skills and the latest learnings in the Android Framework.
## To be able to compile this project, you need to add the following in your global gradle properties file:
### API_KEY
### PRIVATE_API_KEY
### BASE_URL
### TIMESTAMP
#### For this client app I used "https://gateway.marvel.com/v1/public/characters" to fetch the characters list.
#### Used Fresco for a more efficient image loading.
#### Used ViewModels as well.
#### Sealed classes and states where added.
#### Kotlin-coroutines were used for blocking operations(fetching Character list from the server).
#### Used latest Jetpack libraries.
#### Jetpack navigation.
#### Paging 3.

## Architecture:
I am using the MVVM architecture and some state machine concept on top of it. Every screen has a view, a model, and a ViewModel. The ViewModel contains a state that represents the properties of the View. This state will be emitted using Flow to the observer(view).

The ViewModel state is represented using a simple kotlin data class with different fields.

I also use sealed classes to model some repetitive behaviors. Like, when fetching data in an asynchronous fashion, the usual states are Loading, Failed(with the failure), or Success(with the actual data).

Repository is the single source of truth that is used to fetch data(either from the network or from the local storage).

## Future Enhancements:
In addition I will add Anime Tv Series and even include a search bar, improve the UI Design of the app maybe even 

