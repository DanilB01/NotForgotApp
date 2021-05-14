package ru.tsu.lab3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import ru.tsu.lab3.R
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.interfaces.IMainActivity
import ru.tsu.lab3.network.Network
import ru.tsu.lab3.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), IMainActivity {
    private var navController: NavController? = null
    private var presenter: MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(navHostFragment.requireView())
        presenter = MainActivityPresenter(this, this)
        presenter?.chooseNextView()
    }

    override fun goToTasksShow() {
        navController?.navigate(R.id.action_loginFragment_to_tasksShowFragment)
    }
}