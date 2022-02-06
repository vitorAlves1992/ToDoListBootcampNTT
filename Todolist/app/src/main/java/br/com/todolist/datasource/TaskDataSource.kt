package br.com.todolist.datasource

import br.com.todolist.model.Task

object TaskDataSource{

    private val list= arrayListOf<Task>()

    fun getList()= list.toList()

    fun insertTask(task: Task){
        if (task.id==0) {
            list.add(task.copy(id = (list.size + 1)))
        }
        else{
            list.remove(task)
            list.add(task.copy(id = (list.size + 1)))
        }

    }

    fun deletarItem(task: Task) {

        list.remove(task)
    }

    fun findiById(taskId: Int) = list.find { it.id==taskId }



}