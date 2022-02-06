package com.example.testproject.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entity.GitRepository
import com.example.testproject.R
import kotlinx.android.synthetic.main.item_git_repository.view.*

class GitRepositoriesAdapter(
    private val clickCallback: (item :GitRepository) -> Unit
): RecyclerView.Adapter<GitRepositoriesAdapter.ViewHolder>() {

    private val items = mutableListOf<GitRepository>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GitRepositoriesAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_git_repository, parent, false)

        return ViewHolder(layout)
    }

    fun addItems(newItems: List<GitRepository>) {
        items.apply {
            clear()
            addAll(newItems)
            notifyItemRangeChanged(0, size)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GitRepositoriesAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind() = with(itemView) {
            val pos = absoluteAdapterPosition
            val item = items[pos]

            repo_name.text = item.name
            itemView.setOnClickListener {
                clickCallback.invoke(item)
            }
        }
    }
}