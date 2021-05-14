package ru.tsu.lab3

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.tsu.lab3.network.ApiRepo
import ru.tsu.lab3.network.Network
import ru.tsu.lab3.network.Network.curTask
import ru.tsu.lab3.network.Network.token
import ru.tsu.lab3.network.Task
import ru.tsu.lab3.network.TaskForm


private const val TYPE_CATEGORY : Int = 0
private const val TYPE_NOTE : Int = 1

class RecyclerViewAdapter(private var dates: MutableList<Dates>,
                          private val appContext: Context,
                          private val activityContext: Context,
                          private val navGraphView: View
                          )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var navController: NavController = Navigation.findNavController(navGraphView)

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categoryModel: Dates){
            itemView.categoryTextView.text = categoryModel.category?.name
        }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val apiRepo = ApiRepo(Network.retrofit)
        fun bind(noteModel: Dates, isLast: Boolean, appContext: Context, activityContext: Context){
            itemView.titleText.text = noteModel.task?.title
            itemView.descriptionText.text = noteModel.task?.description
            itemView.checkBox.isChecked = noteModel.task!!.done == 1
            if(itemView.checkBox.isChecked){
                makeTextDone()
            }
            itemView.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    makeTextDone()
                    /*GlobalScope.launch {
                        AppDatabase.getDatabase(appContext).noteDao().updateDoneInfo(curUserID, noteModel.noteDB.id, true)
                    }*/
                    GlobalScope.launch {
                        val task = noteModel.task
                        apiRepo.editTask(token, task.id, TaskForm(null, null, 1, null, null, null))
                    }
                }
            }
            /*when(noteModel.noteDB.priority){
                0-> itemView.layout.setBackgroundColor(itemView.resources.getColor(R.color.red))
                1-> itemView.layout.setBackgroundColor(itemView.resources.getColor(R.color.yellow))
                2-> itemView.layout.setBackgroundColor(itemView.resources.getColor(R.color.blue))
                3-> itemView.layout.setBackgroundColor(itemView.resources.getColor(R.color.green))
            }*/
            itemView.layout.setBackgroundColor(Color.parseColor(noteModel.task.priority.color))
            if(isLast){
                val screenDensity: Float = itemView.context.resources.displayMetrics.density
                val param = itemView.layoutParams as ViewGroup.MarginLayoutParams
                param.bottomMargin = 16 * screenDensity.toInt()
                itemView.layoutParams = param
            }
        }

        private fun makeTextDone() {
            itemView.checkBox.isClickable = false
            itemView.titleText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.descriptionText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CATEGORY){
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
            CategoryViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
            NoteViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(dates[position].type == 0) {
            TYPE_CATEGORY
        } else{
            TYPE_NOTE
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_CATEGORY){
            (holder as CategoryViewHolder).bind(dates[position])
        } else {
            var isLast = false
            if(position == dates.size - 1 || getItemViewType(position + 1) == TYPE_CATEGORY)
                isLast = true
            (holder as NoteViewHolder).bind(dates[position], isLast, appContext, activityContext)
            holder.itemView.setOnClickListener {
                val task: Task = dates[position].task!!
                //val action = TasksShowFragmentDirections.actionTasksShowFragmentToNotesDetailsFragment(task.id)
                curTask = task
                navController.navigate(R.id.action_tasksShowFragment_to_notesDetailsFragment)
            }
        }
    }
}