package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.data.models.remote.response.StoriesDetailResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding
import said.shatila.marvelcharacters.util.getUrlImageWithExtension
import said.shatila.marvelcharacters.util.replaceUrlImage

class StoriesDetailAdapter() :
    RecyclerView.Adapter<StoriesDetailAdapter.StoriesDetailViewHolder>() {

    private val mDiffer: AsyncListDiffer<StoriesDetailResponse?> =
        AsyncListDiffer<StoriesDetailResponse?>(
            this,
            object : DiffUtil.ItemCallback<StoriesDetailResponse?>() {
                override fun areItemsTheSame(
                    oldItem: StoriesDetailResponse, newItem: StoriesDetailResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: StoriesDetailResponse, newItem: StoriesDetailResponse
                ): Boolean {
                    return oldItem == newItem
                }
            })

    class StoriesDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(StoriesDetailsResponse: StoriesDetailResponse) {
            with(binding) {
                tvTitle.text = StoriesDetailsResponse.title
                tvDescription.text = StoriesDetailsResponse.description
               /* val getUrl = StoriesDetailsResponse.thumbnail?.path?.replaceUrlImage()
                val imagePath =
                    Uri.parse(
                        StoriesDetailsResponse.thumbnail?.extension?.let { extenstion ->
                            getUrl?.let { url ->
                                getUrlImageWithExtension(
                                    url,
                                    extenstion
                                )
                            }
                        }
                    )
                ivDynamicItem.setImageURI(imagePath)*/
                tvDynamicItem.text = StoriesDetailsResponse.type

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoriesDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDynamicCharacterDetailBinding.inflate(inflater, parent, false)
        return StoriesDetailViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: StoriesDetailViewHolder, position: Int) {
        val StoriesDetailsResponse = mDiffer.currentList[position]
        StoriesDetailsResponse?.let { eventsDetail -> holder.bind(eventsDetail) }

    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(list: List<StoriesDetailResponse?>) {
        mDiffer.submitList(list)
    }
}