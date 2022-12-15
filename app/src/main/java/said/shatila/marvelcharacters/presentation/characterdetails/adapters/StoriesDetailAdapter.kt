package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.R
import said.shatila.marvelcharacters.data.models.remote.response.CommonCharacterDetailResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding

class StoriesDetailAdapter() :
    RecyclerView.Adapter<StoriesDetailAdapter.StoriesDetailViewHolder>() {
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

    class StoriesDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commonCharacterDetailResponse: CommonCharacterDetailResponse) {
            with(binding) {
                tvTitle.text =
                    commonCharacterDetailResponse.title ?: context.getString(R.string.no_title)
                tvDescription.text = commonCharacterDetailResponse.description ?: context.getString(
                    R.string.no_description
                )
                /*    val getUrl = commonCharacterDetailResponse.thumbnail?.path?.replaceUrlImage()
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
                    ivDynamicItem.setImageURI(imagePath)*/
                tvDynamicItem.text = commonCharacterDetailResponse.type

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

    fun submitList(list: List<CommonCharacterDetailResponse?>) {
        mDiffer.submitList(list)
    }
}