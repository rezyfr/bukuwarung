package com.example.bukuwarungtest.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.data.model.User
import kotlinx.android.synthetic.main.item_user_layout.view.*
import java.text.DecimalFormat

class UserAdapter(
    private val listItem: ArrayList<User>,
    private val listener: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return ItemViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int = listItem.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(listItem[position], listener)
    }

    inner class ItemViewHolder(v: View, private val context: Context) : RecyclerView.ViewHolder(v) {
        fun bindItem(data: User, listener: (User) -> Unit) {
            Glide.with(context)
                .load(data.avatar)
                .into(itemView.iv_avatar)
            itemView.tv_name.text = "${data.first_name} ${data.last_name}"
            itemView.tv_email.text = "${data.email}"

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    fun setData(data: List<User>) {
        listItem.addAll(data)
        notifyDataSetChanged()
    }

}