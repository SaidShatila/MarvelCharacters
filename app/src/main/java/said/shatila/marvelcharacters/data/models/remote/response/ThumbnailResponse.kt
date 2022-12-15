package said.shatila.marvelcharacters.data.models.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailResponse(val path: String, val extension: String) : Parcelable
