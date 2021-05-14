package ru.tsu.lab3.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_notes_details.*
import ru.tsu.lab3.R
import ru.tsu.lab3.interfaces.INotesDetails
import ru.tsu.lab3.network.Network.curTask
import ru.tsu.lab3.presenter.LoginPresenter
import ru.tsu.lab3.presenter.NotesDetailsPresenter

class NotesDetailsFragment: Fragment(), INotesDetails {

    private var navController: NavController? = null
    private var presenter: NotesDetailsPresenter? = null
    private var actionBarActivity: AppCompatActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_notes_details, container, false)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        presenter = NotesDetailsPresenter(this)
        actionBarActivity = activity as AppCompatActivity
        actionBarActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter?.setView()

        editImageButton.setOnClickListener {
            goToEditNote()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                actionBarActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                navController?.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun goToEditNote() {
        navController?.navigate(R.id.action_notesDetailsFragment_to_editNoteFragment)
    }

    override fun setTitle(text: String) {
        titleTextView.text = text
    }

    override fun setDescription(text: String) {
        descriptionTextView.text = text
    }

    override fun setCategory(text: String) {
        categoryTextView.text = text
    }

    override fun setDone(isDone: Int) {
        when(isDone) {
            0 -> {
                doneTextView.text = requireContext().getText(R.string.undone)
                doneTextView.setTextColor(requireContext().getColor(R.color.red))
            }
            1 -> {
                doneTextView.text = requireContext().getText(R.string.done)
                doneTextView.setTextColor(requireContext().getColor(R.color.green))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setPriority(priorityText: String) {
        when(priorityText) {
            "Important" -> priorityImageView.setImageDrawable(requireContext().resources.getDrawable(
                R.drawable.ic_priority_important
            ))
            "Very important" -> priorityImageView.setImageDrawable(requireContext().resources.getDrawable(
                R.drawable.ic_priority_very_important
            ))
            "Not important" -> priorityImageView.setImageDrawable(requireContext().resources.getDrawable(
                R.drawable.ic_priority_not_important
            ))
            "May be never" -> priorityImageView.setImageDrawable(requireContext().resources.getDrawable(
                R.drawable.ic_priority_may_be_never
            ))
        }
        priorityTextView.text = priorityText
    }
}