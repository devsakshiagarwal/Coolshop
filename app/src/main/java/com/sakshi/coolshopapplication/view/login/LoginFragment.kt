package com.sakshi.coolshopapplication.view.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.sakshi.coolshopapplication.R
import com.sakshi.coolshopapplication.arch.BaseFragment
import com.sakshi.coolshopapplication.arch.SharedPref
import com.sakshi.coolshopapplication.model.repository.LoginRepository
import com.sakshi.coolshopapplication.model.schema.Login
import com.sakshi.coolshopapplication.model.schema.LoginDetails
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment(), LoginRepository.LoginListener {

    private lateinit var loginRepository: LoginRepository
    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginRepository = compRoot()!!.getLoginRepo()
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.setRepository(compRoot()!!.getRouter(), loginRepository)
        initViews()
        observeResponse()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelApiCall()
    }

    override fun onLoginSuccess(login: Login) {
        SharedPref.saveAuthToken(this.requireActivity(), login.token)
        SharedPref.saveUserId(this.requireActivity(), login.userId)
        SharedPref.saveEmail(this.requireActivity(), et_email.text.toString())
        SharedPref.savePassword(this.requireActivity(), et_password.text.toString())
        compRoot()!!.getRouter().toDisplayPage()
    }

    override fun onFailure(error: String) {
        Toast.makeText(context, getString(R.string.error_login), Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        button_login.setOnClickListener {
            viewModel.doLogin(LoginDetails(et_email.text.toString(), et_password.text.toString()))
        }
    }

    private fun observeResponse() {
        viewModel.liveDataErrorEmail.observe(viewLifecycleOwner, Observer {
            if (it)
                Toast.makeText(
                    context,
                    getString(R.string.invalid_email),
                    Toast.LENGTH_SHORT
                ).show()
        })
        viewModel.liveDataErrorPassword.observe(viewLifecycleOwner, Observer {
            if (it)
                Toast.makeText(
                    context,
                    getString(R.string.invalid_password),
                    Toast.LENGTH_SHORT
                ).show()
        })
        viewModel.liveDataLoginSuccess.observe(viewLifecycleOwner, Observer {
            if (it)
                onLoginSuccess(Login("1234", "qwertyuiop"))
        })
    }
}
