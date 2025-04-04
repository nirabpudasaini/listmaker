package com.pudasaini.listmaker

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.pudasaini.listmaker.models.TaskList

class MainViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    lateinit var onListAdded: (() -> Unit)

    val lists: MutableList<TaskList> by lazy{
        retrievelists()
    }

    private fun retrievelists(): MutableList<TaskList>{
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferencesContents){
            val itemHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemHashSet)
            taskLists.add(list)
        }

        return taskLists
    }

    fun saveList(list: TaskList){
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }
}