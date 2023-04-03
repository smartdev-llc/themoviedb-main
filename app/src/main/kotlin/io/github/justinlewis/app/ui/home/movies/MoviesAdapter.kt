package io.github.justinlewis.app.ui.home.movies

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import butterknife.BindView
import io.github.justinlewis.app.IMAGE_PATH
import io.github.justinlewis.app.R
import io.github.justinlewis.app.R.layout
import io.github.justinlewis.app.ui.base.adapter.BaseViewHolder
import io.github.justinlewis.app.ui.base.adapter.createView
import io.github.justinlewis.app.util.loadUrl
import io.github.justinlewis.domain.pojo.Movie

/**
 * Created on 5/16/2019.
 */
class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): MovieViewHolder =
        MovieViewHolder(createView(parent, layout.item_movie))

    override fun onBindViewHolder(holder: MovieViewHolder, pos: Int) {
        val movie = getItem(pos)
        movie?.let {
            holder.setTitle(movie.title)
            if (movie.posterPath.isNotEmpty()) {
                holder.loadImageUrl("$IMAGE_PATH${movie.posterPath}")
            }
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}

class MovieViewHolder(itemView: View) : BaseViewHolder(itemView) {
    @BindView(R.id.iv_poster)
    internal lateinit var vPoster: ImageView
    @BindView(R.id.tv_title)
    internal lateinit var vTitle: TextView

    fun loadImageUrl(url: String) {
        vPoster.loadUrl(url)
    }

    fun setTitle(title: String) {
        vTitle.text = title
    }
}
