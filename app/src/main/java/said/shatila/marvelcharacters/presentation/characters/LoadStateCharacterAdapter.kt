package said.shatila.marvelcharacters.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import said.shatila.marvelcharacters.databinding.ItemMotionLoaderBinding

class LoadStateCharacterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateCharacterAdapter.LoadStateViewHolder>() {


    class LoadStateViewHolder(
        private val ItemLoadingState: ItemMotionLoaderBinding,
        private val retry: () -> Unit,
    ) :
        RecyclerView.ViewHolder(ItemLoadingState.root) {

        private val motionLayout: MotionLayout = ItemLoadingState.mlLoader
        fun bind(loadState: LoadState) {
            with(ItemLoadingState) {
                btnRetry.setOnClickListener {
                    retry()
                }

            }
            if (loadState is LoadState.Loading) {
                ItemLoadingState.pbLoader.isVisible = true
                motionLayout.transitionToEnd()
            } else {
                ItemLoadingState.pbLoader.isVisible = false

                motionLayout.transitionToStart()
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemMotionLoaderBinding.inflate(inflater, parent, false)
        return LoadStateViewHolder(view, retry)
    }
}