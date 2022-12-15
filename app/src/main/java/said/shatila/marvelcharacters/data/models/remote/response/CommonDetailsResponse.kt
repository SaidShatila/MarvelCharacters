package said.shatila.marvelcharacters.data.models.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommonDetailsResponse(val name: String, val resourceURI: String, val type: String?) :
    Parcelable
