package ru.tsu.lab3.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.`$Gson$Preconditions`
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tasks_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.Dates
import ru.tsu.lab3.R
import ru.tsu.lab3.RecyclerViewAdapter
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.interfaces.ITasksShow
import ru.tsu.lab3.network.ApiRepo
import ru.tsu.lab3.network.Category
import ru.tsu.lab3.network.Network
import ru.tsu.lab3.network.Network.curTask
import ru.tsu.lab3.network.Task
import ru.tsu.lab3.presenter.TasksShowPresenter

class TasksShowFragment: Fragment(), ITasksShow {
    private var presenter: TasksShowPresenter? = null
    private var navController: NavController? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tasks_show, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TasksShowPresenter(this, requireActivity())
        navController = Navigation.findNavController(view)
        recyclerView = mainRecyclerView as RecyclerView

        presenter?.initNotesList()

        addNoteButton.setOnClickListener {
            presenter?.addNotePressed()
        }

        swipeContainer.setOnRefreshListener {
            presenter?.initNotesList()
            swipeContainer.isRefreshing = false
        }

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeDirs (recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                if (viewHolder is RecyclerViewAdapter.CategoryViewHolder) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                presenter?.taskSwiped(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutButton -> {
                presenter?.logoutPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun goToEditNote() {
        navController?.navigate(R.id.action_tasksShowFragment_to_editNoteFragment)
    }

    override fun goToLogin() {
        navController?.navigate(R.id.action_tasksShowFragment_to_loginFragment)
    }

    override fun removePlaceholder() {
        noTasksGroup.visibility = View.GONE
    }

    override fun setPlaceholder() {
        noTasksGroup.visibility = View.VISIBLE
    }

    override fun showLogoutMessage() {
        Toast.makeText(activity, "You were logged out", Toast.LENGTH_SHORT).show()
    }

    override fun showTaskDeletedMessage() {
        Toast.makeText(activity, "Note was deleted", Toast.LENGTH_SHORT).show()
    }

    override fun showNoConnectionMessage() {
        Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
    }

    override fun setRecyclerView(resultArray: MutableList<Dates>) {
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val adapter = RecyclerViewAdapter(
            resultArray,
            requireActivity().applicationContext,
            requireActivity(),
            navHostFragment.requireView()
        )
        recyclerView?.adapter = adapter
    }

    override fun changeRecyclerAdapter() {
        recyclerView?.adapter?.notifyDataSetChanged()
    }

}