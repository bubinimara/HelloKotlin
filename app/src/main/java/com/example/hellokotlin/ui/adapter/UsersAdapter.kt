package com.example.hellokotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.util.ImageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 *
 * Created by Davide Parise on 05/09/21.
 */
class UsersAdapter(val imageUtil: ImageUtil):RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    private val items:MutableList<User> = ArrayList()

    /**
     * The view holder
     */
    class ViewHolder(itemView: View,val imageUtil: ImageUtil) : RecyclerView.ViewHolder(itemView) {
        private val name:TextView
        private val image:ImageView

        init {
            name = itemView.findViewById(R.id.name)
            image = itemView.findViewById(R.id.imageView)
        }
        fun set(user: User){
            name.text = user.name
            val img = imageUtil.getImageUrlForUser(user)
            Glide.with(itemView)
                .load(img)
                .circleCrop()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_user, parent, false)
        return ViewHolder(view,imageUtil)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /** CUSTOM **/

    public fun add(users:List<User>){
        items.addAll(users)
        notifyItemRangeInserted(0,users.size)
    }

    public fun clear(){
        val lastSize = items.size
        items.clear()
        notifyItemRangeRemoved(0,lastSize)

    }
}