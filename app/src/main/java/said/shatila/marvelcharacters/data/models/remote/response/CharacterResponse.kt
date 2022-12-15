package said.shatila.marvelcharacters.data.models.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val thumbnail: ThumbnailResponse,
    val resourceURI: String,
    val comics: ComicsResponse,
):Parcelable