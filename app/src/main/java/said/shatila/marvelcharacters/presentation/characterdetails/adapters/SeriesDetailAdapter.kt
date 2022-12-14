package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.data.models.remote.response.SeriesDetailResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding
import said.shatila.marvelcharacters.util.getUrlImageWithExtension
import said.shatila.marvelcharacters.util.replaceUrlImage

class SeriesDetailAdapter() : RecyclerView.Adapter<SeriesDetailAdapter.SeriesDetailViewHolder>() {

    private val mDiffer: AsyncListDiffer<SeriesDetailResponse?> =
        AsyncListDiffer<SeriesDetailResponse?>(
            this,
            object : DiffUtil.ItemCallback<SeriesDetailResponse?>() {
                override fun areItemsTheSame(
                    oldItem: SeriesDetailResponse, newItem: SeriesDetailResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SeriesDetailResponse, newItem: SeriesDetailResponse
                ): Boolean {
                    return oldItem == newItem
                }
            })

    class SeriesDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(SeriesDetailsResponse: SeriesDetailResponse) {
            with(binding) {
                tvTitle.text = SeriesDetailsResponse.title
                tvDescription.text = SeriesDetailsResponse.description
                val getUrl = SeriesDetailsResponse.thumbnail?.path?.replaceUrlImage()
                val imagePath =
                    Uri.parse(
                        SeriesDetailsResponse.thumbnail?.extension?.let { extenstion ->
                            getUrl?.let { url ->
                                getUrlImageWithExtension(
                                    url,
                                    extenstion
                                )
                            }
                        }
                    )
                ivDynamicItem.setImageURI(imagePath)
                val nextPrev =
                    "${SeriesDetailsResponse.next?.name} - ${SeriesDetailsResponse.previous?.name}"
                tvDynamicItem.text = nextPrev

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDynamicCharacterDetailBinding.inflate(inflater, parent, false)
        return SeriesDetailViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: SeriesDetailViewHolder, position: Int) {
        val SeriesDetailsResponse = mDiffer.currentList[position]
        SeriesDetailsResponse?.let { eventsDetail -> holder.bind(eventsDetail) }

    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(list: List<SeriesDetailResponse?>) {
        mDiffer.submitList(list)
    }
}