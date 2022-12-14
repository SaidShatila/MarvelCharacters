package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.data.models.remote.response.EventsDetailResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding
import said.shatila.marvelcharacters.util.getUrlImageWithExtension
import said.shatila.marvelcharacters.util.replaceUrlImage

class EventDetailAdapter() :
    RecyclerView.Adapter<EventDetailAdapter.EventDetailViewHolder>() {

    private val mDiffer: AsyncListDiffer<EventsDetailResponse?> =
        AsyncListDiffer<EventsDetailResponse?>(
            this,
            object : DiffUtil.ItemCallback<EventsDetailResponse?>() {
                override fun areItemsTheSame(
                    oldItem: EventsDetailResponse, newItem: EventsDetailResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: EventsDetailResponse, newItem: EventsDetailResponse
                ): Boolean {
                    return oldItem == newItem
                }
            })

    class EventDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(EventsDetailResponse: EventsDetailResponse) {
            with(binding) {
                tvTitle.text = EventsDetailResponse.title
                tvDescription.text = EventsDetailResponse.description
                val getUrl = EventsDetailResponse.thumbnail?.path?.replaceUrlImage()
                val imagePath =
                    Uri.parse(
                        EventsDetailResponse.thumbnail?.extension?.let { extenstion ->
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
                    "${EventsDetailResponse.next?.name} - ${EventsDetailResponse.previous?.name}"
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
        val EventsDetailResponse = mDiffer.currentList[position]
        EventsDetailResponse?.let { eventsDetail -> holder.bind(eventsDetail) }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(list: List<EventsDetailResponse?>) {
        mDiffer.submitList(list)
    }
}