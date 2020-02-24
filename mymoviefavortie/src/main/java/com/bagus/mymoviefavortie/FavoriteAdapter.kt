package com.bagus.mymoviefavortie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.favorite_cardview.view.*


class FavoriteAdapter( private var listFavorites : ArrayList<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_cardview, parent, false)
        return FavoriteViewHolder(view)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
//        holder.itemView.delete_button.setOnClickListener{
//            listFavorites.removeAt(position)
//            notifyItemRemoved(position)
//            notifyDataSetChanged()
//
//
//        }
        holder.bind(listFavorites[position])

    }
    fun getItem() : ArrayList<Favorite>{
        return listFavorites
    }
    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: Favorite) {
            with(itemView){
                title_fav.text = fav.title
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(context!!).load("https://image.tmdb.org/t/p/w342/"+fav.poster).apply(options).into(itemView.coverImageView)
                itemView.setOnClickListener {
//                    val moveWithDataIntent = Intent(context, DetailItem::class.java)
//
//                    if(fav.type == "MOVIE"){
//                        var data = Movie(fav.item_id,fav.title,fav.poster)
//                        moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "MOVIE")
//                        moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, data)
//                    }
//
//                    if(fav.type == "TV"){
//                        var data = Tv(fav.item_id,fav.title,fav.poster)
//                        moveWithDataIntent.putExtra(DetailItem.EXTRA_CLASS, "TV")
//                        moveWithDataIntent.putExtra(DetailItem.EXTRA_DATA, data)
//                    }
//
//                    context?.startActivity(moveWithDataIntent)

                }




            }
        }
    }


}