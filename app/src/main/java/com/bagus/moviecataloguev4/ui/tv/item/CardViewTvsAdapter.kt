package com.bagus.moviecataloguev4.ui.tv.item

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagus.moviecataloguev4.DetailItem
import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.model.tv.Tv
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class CardViewTvsAdapter (private val listTv: ArrayList<Tv>) : RecyclerView.Adapter<CardViewTvsAdapter.CardViewViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview, viewGroup, false)
        context = viewGroup.context
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(cardViewViewHolder: CardViewViewHolder, i: Int) {
        val tv = listTv[i]
        try {
            if (tv.poster_path != null && tv.poster_path != "" ){
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(context!!).load("https://image.tmdb.org/t/p/w342/"+tv.poster_path).apply(options).into(cardViewViewHolder.poster)
            }

        }  catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

        cardViewViewHolder.title.text = tv.name

        cardViewViewHolder.itemView.setOnClickListener {
            if(context != null){
                val moveWithDataIntent = Intent(context, DetailItem::class.java)
                moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "TV")
                moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, listTv[i])
                context!!.startActivity(moveWithDataIntent)
            }else {
                context = it.getContext()
            }

        }


    }

    override fun getItemCount(): Int {
        return listTv.size
    }

    inner class CardViewViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var poster: ImageView
        internal var title: TextView
        init {
            poster = itemView.findViewById(R.id.coverImageView)
            title = itemView.findViewById(R.id.title_fav)

        }

    }


}