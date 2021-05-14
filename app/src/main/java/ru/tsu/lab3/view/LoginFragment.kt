package ru.tsu.lab3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import ru.tsu.lab3.R
import ru.tsu.lab3.interfaces.ILogin
import ru.tsu.lab3.presenter.LoginPresenter

class LoginFragment: Fragment(), ILogin {

    private var navController: NavController? = null
    private var presenter: LoginPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        presenter = LoginPresenter(this, requireActivity())

        registrationTextView.setOnClickListener {
            goToRegistration()
        }

        enterButton.setOnClickListener {
            presenter?.showNextFragment()
        }
    }

    override fun goToRegistration() {
        navController?.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun goToTasksShow() {
        navController?.navigate(R.id.action_loginFragment_to_tasksShowFragment)
    }

    override fun getEmail(): String = emailEditText.text.toString()

    override fun getPassword(): String = passwordEditText.text.toString()

    override fun showLoginError() {
        Toast.makeText(activity, "No such users found", Toast.LENGTH_SHORT).show()
    }

    override fun showNoConnectionMessage() {
        Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}