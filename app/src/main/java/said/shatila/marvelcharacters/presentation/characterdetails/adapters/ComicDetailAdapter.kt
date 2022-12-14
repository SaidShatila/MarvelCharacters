package said.shatila.marvelcharacters.presentation.characterdetails.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.data.models.remote.response.ComicDetailsResponse
import said.shatila.marvelcharacters.databinding.ItemDynamicCharacterDetailBinding
import said.shatila.marvelcharacters.util.getUrlImageWithExtension
import said.shatila.marvelcharacters.util.replaceUrlImage

class ComicDetailAdapter() :
    RecyclerView.Adapter<ComicDetailAdapter.ComicDetailViewHolder>() {

    private val mDiffer: AsyncListDiffer<ComicDetailsResponse?> =
        AsyncListDiffer<ComicDetailsResponse?>(
            this,
            object : DiffUtil.ItemCallback<ComicDetailsResponse?>() {
                override fun areItemsTheSame(
                    oldItem: ComicDetailsResponse, newItem: ComicDetailsResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ComicDetailsResponse, newItem: ComicDetailsResponse
                ): Boolean {
                    return oldItem == newItem
                }
            })

    class ComicDetailViewHolder(
        val binding: ItemDynamicCharacterDetailBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comicDetailsResponse: ComicDetailsResponse) {
            with(binding) {
                tvTitle.text = comicDetailsResponse.title
                tvDescription.text = comicDetailsResponse.description
                val getUrl = comicDetailsResponse.thumbnail?.path?.replaceUrlImage()
                val imagePath =
                    Uri.parse(
                        comicDetailsResponse.thumbnail?.extension?.let { extenstion ->
                            getUrl?.let { url ->
                                getUrlImageWithExtension(
                                    url,
                                    extenstion
                                )
                            }
                        }
                    )
                ivDynamicItem.setImageURI(imagePath)
                comicDetailsResponse.prices?.forEach { priceResponse ->
                    tvDynamicItem.text = priceResponse.price.toString()

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDynamicCharacterDetailBinding.inflate(inflater, parent, false)
        return ComicDetailViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ComicDetailViewHolder, position: Int) {
        val comicDetailsResponse = mDiffer.currentList[position]
        comicDetailsResponse?.let { comicDetails -> holder.bind(comicDetails) }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(list: List<ComicDetailsResponse?>) {
        mDiffer.submitList(list)
    }
}