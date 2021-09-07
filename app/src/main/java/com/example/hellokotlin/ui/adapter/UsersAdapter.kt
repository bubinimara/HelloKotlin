package com.example.hellokotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.User


/**
 *
 * Created by Davide Parise on 05/09/21.
 */
class UsersAdapter:RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    private val items:MutableList<User> = ArrayList()

    /**
     * The view holder
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name:TextView
        init {
            name = itemView.findViewById(R.id.name)
        }
        fun set(user: User){
            name.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_user, parent, false)
        return ViewHolder(view)
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