package said.shatila.marvelcharacters.data.models.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsResponse(
    val available: Int,
    val collectionURI: String,
    val items: List<CommonDetailsResponse>,
    val returned: Int
) : Parcelable