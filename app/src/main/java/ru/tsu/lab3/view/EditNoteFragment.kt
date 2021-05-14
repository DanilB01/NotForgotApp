package ru.tsu.lab3.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.util.StringUtil
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.R
import ru.tsu.lab3.interfaces.IEditNote
import ru.tsu.lab3.network.*
import ru.tsu.lab3.network.Network.curTask
import ru.tsu.lab3.presenter.EditNotePresenter
import ru.tsu.lab3.presenter.LoginPresenter
import java.util.*

class EditNoteFragment: Fragment(), IEditNote {
    private var presenter: EditNotePresenter? = null
    private var navController: NavController? = null
    private var dateCalendar: Calendar = Calendar.getInstance()
    private var actionBarActivity: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EditNotePresenter(this, requireActivity())
        navController = Navigation.findNavController(view)
        actionBarActivity = activity as AppCompatActivity
        actionBarActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter?.setInitData()

        saveButton.setOnClickListener {
            presenter?.saveTask()
        }

        dateImageButton.setOnClickListener{
            setDate()
        }

        dateEditText.setOnClickListener {
            setDate()
        }

        addCategoryImageButton.setOnClickListener {v ->
            showAddCategoryDialog(v)
        }
    }

    private fun setInitialDate() {
        dateEditText.setText(
            DateUtils.formatDateTime(
                activity,
                dateCalendar.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
        )
    }

    var d =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateCalendar[Calendar.YEAR] = year
            dateCalendar[Calendar.MONTH] = monthOfYear
            dateCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            setInitialDate()
        }

    private fun setDate() {
        DatePickerDialog(
            requireActivity(), d,
            dateCalendar.get(Calendar.YEAR),
            dateCalendar.get(Calendar.MONTH),
            dateCalendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter?.backButtonPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setCategoryAdapter(options: Array<String>) {
        val spinnerAdapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.item_spinner, options
        )
        categorySpinner.adapter = spinnerAdapter
    }

    override fun setPriorityAdapter(prioritiesNames: Array<String>) {
        val prioritySpinnerAdapter = ArrayAdapter<String>(
                requireActivity(),
            R.layout.item_spinner, prioritiesNames
        )
        prioritySpinner.adapter = prioritySpinnerAdapter
    }

    override fun setTaskTitle(text: String) {
        titleEditText.setText(text)
    }

    override fun setTaskDescription(text: String?) {
        descriptionEditText.setText(text)
    }

    override fun ifFieldsEmpty(): Boolean {
        if (TextUtils.isEmpty(titleEditText.text) || TextUtils.isEmpty(descriptionEditText.text) ||
            TextUtils.isEmpty(dateEditText.text) || categorySpinner.adapter.count == 0
        ) {
            Toast.makeText(activity, "Not every field has a value", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun setNewCategoryInAdapter() {
        categorySpinner.setSelection(categorySpinner.adapter.count - 1)
    }

    override fun setAdapterCategoryByID(id: Int) {
        categorySpinner.setSelection(id)
    }

    override fun setAdapterPriorityByID(id: Int) {
        prioritySpinner.setSelection(id)
    }

    override fun getTaskTitleInput(): String{
        return titleEditText.text.toString()
    }

    override fun getTaskDescriptionInput(): String{
        return descriptionEditText.text.toString()
    }

    override fun getTaskDateInput(): String{
        return dateEditText.text.toString()
    }

    override fun getTaskCategoryInput(): String{
        return categorySpinner.selectedItem.toString()
    }

    override fun getTaskPriorityInput(): String{
        return prioritySpinner.selectedItem.toString()
    }

    override fun getNewCategoryName(dialogView: View): String {
        return dialogView.dialogEditText.text.toString()
    }

    override fun goToTasksShow() {
        actionBarActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navController?.navigate(R.id.action_editNoteFragment_to_tasksShowFragment)
        navController?.popBackStack()
    }

    override fun goBack() {
        actionBarActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navController?.popBackStack()
    }

    override fun showAddCategoryDialog(v: View) {
        val builder = AlertDialog.Builder(activity)
        val dialogView = LayoutInflater.from(v.context)
            .inflate(R.layout.dialog_layout, null, false)
        builder.setView(dialogView)
        builder.setPositiveButton(getString(R.string.save)) {
                dialog, which ->
            presenter?.saveNewCategory(dialogView)
        }
        builder.setNegativeButton(getString(R.string.cancelling)){
                dialog, which ->
        }

        val alertDialog = builder.create()
        alertDialog.show()

        val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)

        negativeButton.setTextColor(resources.getColor(R.color.blueLinesColor))
        positiveButton.setTextColor(resources.getColor(R.color.blueLinesColor))
    }

    override fun showNoConnectionMessage() {
        Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
    }

}