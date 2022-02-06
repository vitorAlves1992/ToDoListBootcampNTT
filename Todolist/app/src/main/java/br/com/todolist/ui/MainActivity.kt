package br.com.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.todolist.databinding.ActivityMainBinding
import br.com.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter= adapter
        insertListener()
        adapter.submitList(TaskDataSource.getList())
        updateList()
    }

    fun insertListener(){

        binding.fab.setOnClickListener {

            val intent= Intent(this,AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
            updateList()

        }
        adapter.listenerEdit={

            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)


        }
        adapter.listenerDelet={

            TaskDataSource.deletarItem(it)
            updateList()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== CREATE_NEW_TASK && resultCode==Activity.RESULT_OK) updateList()

    }


    private fun updateList(){
        val list = TaskDataSource.getList()

        if (list.isEmpty()) {
           binding.includeEmpty.imgEmptyState.visibility = View.VISIBLE
            binding.includeEmpty.tvMessage.visibility = View.VISIBLE
        }
        else{
            binding.includeEmpty.imgEmptyState.visibility = View.GONE
            binding.includeEmpty.tvMessage.visibility = View.GONE
        }
        adapter.submitList(list)
    }
    companion object {
        private const val  CREATE_NEW_TASK=1000
    }


}