package br.com.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.todolist.databinding.ActivityAddTaskBinding
import br.com.todolist.extensions.format
import br.com.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class addTaskActivity :AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
    }

}