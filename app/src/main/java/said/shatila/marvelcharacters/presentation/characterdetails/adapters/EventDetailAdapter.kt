package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.R
import said.shatila.marvelcharacters.data.models.remote.response.CommonCharacterDetailResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding
import said.shatila.marvelcharacters.util.getUrlImageWithExtension
import said.shatila.marvelcharacters.util.replaceUrlImage

class EventDetailAdapter() :
    RecyclerView.Adapter<EventDetailAdapter.EventDetailViewHolder>() {

    private val mDiffer: AsyncListDiffer<CommonCharacterDetailResponse?> =
        AsyncListDiffer<CommonCharacterDetailResponse?>(
            this,
            object : DiffUtil.ItemCallback<CommonCharacterDetailResponse?>() {
                override fun areItemsTheSame(
                    oldItem: CommonCharacterDetailResponse, newItem: CommonCharacterDetailResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: CommonCharacterDetailResponse, newItem: CommonCharacterDetailResponse
                ): Boolean {
                    return oldItem == newItem
                }
            })

    class EventDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commonCharacterDetailResponse: CommonCharacterDetailResponse) {
            with(binding) {
                tvTitle.text = commonCharacterDetailResponse.title ?: context.getString(R.string.no_title)
                tvDescription.text = commonCharacterDetailResponse.description ?: context.getString(
                    R.string.no_description
                )
                val getUrl = commonCharacterDetailResponse.thumbnail?.path?.replaceUrlImage()
                val imagePath =
                    Uri.parse(
                        commonCharacterDetailResponse.thumbnail?.extension?.let { extenstion ->
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
                    "${commonCharacterDetailResponse.next?.name} - ${commonCharacterDetailResponse.previous?.name}"
                tvDynamicItem.text = nextPrev

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDynamicCharacterDetailBinding.inflate(inflater, parent, false)
        return EventDetailViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: EventDetailViewHolder, position: Int) {
        val CommonCharacterDetailResponse = mDiffer.currentList[position]
        CommonCharacterDetailResponse?.let { eventsDetail -> holder.bind(eventsDetail) }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(list: List<CommonCharacterDetailResponse?>) {
        mDiffer.submitList(list)
    }
}