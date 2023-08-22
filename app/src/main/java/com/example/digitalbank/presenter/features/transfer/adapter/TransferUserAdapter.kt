package com.example.digitalbank.presenter.features.transfer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digitalbank.R
import com.example.digitalbank.data.model.User
import com.example.digitalbank.databinding.TransferUserItemBinding

class TransferUserAdapter(
    private val userSelected: (User) -> Unit
) : ListAdapter<User, TransferUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransferUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        with(holder.binding) {
            tvUsername.text = user.name
            val userImage = user.image.ifEmpty { R.drawable.ic_image_profile }

            Glide.with(root.context)
                .load(userImage)
                .into(imgUser)
        }

        holder.itemView.setOnClickListener {
            userSelected(user)
        }
    }

    inner class ViewHolder(val binding: TransferUserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}