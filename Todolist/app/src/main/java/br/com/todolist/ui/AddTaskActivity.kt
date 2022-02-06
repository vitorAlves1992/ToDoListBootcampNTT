package br.com.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.todolist.databinding.ActivityAddTaskBinding
import br.com.todolist.datasource.TaskDataSource
import br.com.todolist.extensions.format
import br.com.todolist.extensions.text
import br.com.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity :AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            val intExtra = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findiById(intExtra)?.let {
                binding.tilInput.text= it.title
                binding.tilHour.text= it.hour
                binding.tilDate.text= it.date
            }
        }
        insertListener()
    }

    private fun insertListener(){

        binding.tilDate.editText?.setOnClickListener(){
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val default = TimeZone.getDefault()
                val offset = default.getOffset(Date().time)*-1
                binding.tilDate.text = Date(it+offset).format()
            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener(){


            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Escolha o horário")
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val hour= if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                val minute= if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                binding.tilHour.text= "$hour:$minute"
            }
            timePicker.show(supportFragmentManager,null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnCriarTarefa.setOnClickListener {

            val task = Task(
                title = binding.tilInput.text,
                hour = binding.tilHour.text,
                date = binding.tilDate.text,
                id= intent.getIntExtra(TASK_ID,0)

            )

          TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    companion object{

        const val TASK_ID="task_id"
    }

}