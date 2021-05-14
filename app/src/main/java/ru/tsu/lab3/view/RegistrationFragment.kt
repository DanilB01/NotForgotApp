package ru.tsu.lab3.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.tsu.lab3.R
import ru.tsu.lab3.interfaces.IRegistration
import ru.tsu.lab3.presenter.RegistrationPresenter

class RegistrationFragment: Fragment(), IRegistration {
    private var navController: NavController? = null
    private var presenter: RegistrationPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        presenter = RegistrationPresenter(this, requireActivity())

        loginTextView.setOnClickListener {
            goToLogin()
        }

        logInButton.setOnClickListener {
            presenter?.showNextFragment()
        }

    }

    override fun goToLogin() {
        navController?.navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    override fun goToTasksShow() {
        navController?.navigate(R.id.action_registrationFragment_to_tasksShowFragment)
    }

    override fun checkFieldsEmptiness(): Boolean {
        return (TextUtils.isEmpty(surnameEditText.text) || TextUtils.isEmpty(emailEditText.text) ||
                TextUtils.isEmpty(passwordEditText.text) || TextUtils.isEmpty(repeatPasswordEditText.text))
    }

    override fun getEmail(): String = emailEditText.text.toString()

    override fun getName(): String = surnameEditText.text.toString()

    override fun getPassword(): String = passwordEditText.text.toString()

    override fun ifPasswordsSame(): Boolean {
        return (passwordEditText.text.toString() != repeatPasswordEditText.text.toString())
    }

    override fun showEmptyFieldsMessage() {
        Toast.makeText(activity, "Not every field has a value", Toast.LENGTH_SHORT).show()
    }

    override fun showDifferentPasswordsMessage() {
        Toast.makeText(activity, "Passwords are different", Toast.LENGTH_SHORT).show()
    }

    override fun showRegisterError() {
        Toast.makeText(activity, "Something went wrong. Check your internet connection or input values", Toast.LENGTH_LONG).show()
    }

    override fun showNoConnectionMessage() {
        Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}