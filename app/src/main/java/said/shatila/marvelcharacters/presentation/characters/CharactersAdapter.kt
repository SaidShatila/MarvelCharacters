package said.shatila.marvelcharacters.presentation.characters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.databinding.ItemCharacterBinding


class CharactersAdapter(val onItemClicked: (character: CharacterResponse) -> Unit) :
    PagingDataAdapter<CharacterResponse, CharactersAdapter.CharacterAdapterViewHolder>(
        CharacterComparator
    ) {
    class CharacterAdapterViewHolder(
        val binding: ItemCharacterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            characterResponse: CharacterResponse,
            onItemClicked: () -> Unit,
        ) {
            with(binding) {
                tvHeroName.text = characterResponse.name
                val getUrl = characterResponse.thumbnail.path.replace("http", "https")
                val imagePath =
                    Uri.parse("${getUrl}.${characterResponse.thumbnail.extension}")
                ivHero.setImageURI(imagePath)
                this.root.setOnClickListener {
                    onItemClicked()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterAdapterViewHolder, position: Int) {
        val character = getItem(position)!!
        holder.bind(character) { onItemClicked(character) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterAdapterViewHolder(binding)
    }
}

object CharacterComparator : DiffUtil.ItemCallback<CharacterResponse>() {
    override fun areItemsTheSame(
        oldItem: CharacterResponse,
        newItem: CharacterResponse,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterResponse,
        newItem: CharacterResponse,
    ): Boolean {
        return oldItem == newItem
    }
}

