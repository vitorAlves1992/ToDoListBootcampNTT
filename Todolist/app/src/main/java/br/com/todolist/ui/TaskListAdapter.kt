package br.com.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.todolist.R
import br.com.todolist.databinding.ItemTaskBinding
import br.com.todolist.model.Task

    class TaskListAdapter:ListAdapter<Task,TaskListAdapter.TaskViewHolder>(DiffCallback()){

        var listenerEdit:(Task)-> Unit ={}
        var listenerDelet:(Task)-> Unit ={}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
      inner class TaskViewHolder(
            private val binding: ItemTaskBinding
        ):RecyclerView.ViewHolder(binding.root){
            fun bind(item: Task) {
                binding.tvTitle.text = item.title
                binding.tvDate.text= "${item.date} ${item.hour}"
                binding.ivMenu.setOnClickListener {
                    showPopUp(item)

                }
            }

            private fun showPopUp(item: Task) {
                val ivMenu = binding.ivMenu
                val popupMenu = PopupMenu(ivMenu.context, ivMenu)
                popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {

                    when(it.itemId){

                        R.id.menu_edit->listenerEdit(item)
                        R.id.menu_deletar-> listenerDelet(item)

                    }

                    return@setOnMenuItemClickListener true
                }

                popupMenu.show()
            }

        }


    }
class DiffCallback:DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem== newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task)= oldItem.id== newItem.id

}

