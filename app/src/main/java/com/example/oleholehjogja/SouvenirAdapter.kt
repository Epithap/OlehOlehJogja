package com.example.oleholehjogja

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class SouvenirAdapter(
    private val context: Context,
    private var items: List<Souvenir>,
    private val onItemClick: (Souvenir) -> Unit,
    private val onBookmarkClick: (Souvenir) -> Unit,
    private val onCartClick: (Souvenir) -> Unit
) : RecyclerView.Adapter<SouvenirAdapter.ViewHolder>() {

    fun updateData(newItems: List<Souvenir>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_souvenir_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivSouvenir: ImageView = itemView.findViewById(R.id.ivSouvenir)
        private val tvCategoryBadge: TextView = itemView.findViewById(R.id.tvCategoryBadge)
        private val btnQuickBookmark: FrameLayout = itemView.findViewById(R.id.btnQuickBookmark)
        private val ivBookmarkIcon: ImageView = itemView.findViewById(R.id.ivBookmarkIcon)
        private val btnAddToCart: View = itemView.findViewById(R.id.btnAddToCart)

        private val tvSouvenirName: TextView = itemView.findViewById(R.id.tvSouvenirName)
        private val tvSouvenirSubtitle: TextView = itemView.findViewById(R.id.tvSouvenirSubtitle)
        private val tvSouvenirDesc: TextView = itemView.findViewById(R.id.tvSouvenirDesc)
        private val tvShelfLife: TextView = itemView.findViewById(R.id.tvShelfLife)
        private val tvPriceRange: TextView = itemView.findViewById(R.id.tvPriceRange)

        fun bind(item: Souvenir) {
            ivSouvenir.setImageResource(item.imageResId)
            tvCategoryBadge.text = item.categoryLabel
            tvSouvenirName.text = item.name
            tvSouvenirSubtitle.text = item.subtitle
            tvSouvenirDesc.text = item.description
            tvShelfLife.text = item.shelfLife
            tvPriceRange.text = item.priceRange.split(" - ").firstOrNull() ?: item.priceRange

            updateBookmarkState(item)

            btnQuickBookmark.setOnClickListener {
                onBookmarkClick(item)
            }

            btnAddToCart.setOnClickListener {
                onCartClick(item)
            }


            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        private fun updateBookmarkState(item: Souvenir) {
            val isBookmarked = BookmarkManager.isBookmarked(context, item.id)
            if (isBookmarked) {
                ivBookmarkIcon.setImageResource(R.drawable.ic_bookmark)
                ivBookmarkIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.terracotta))
            } else {
                ivBookmarkIcon.setImageResource(R.drawable.ic_bookmark_border)
                ivBookmarkIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.sogan))
            }
        }
    }
}

