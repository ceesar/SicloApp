package com.siclo.app.ui.login.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.siclo.app.R
import com.siclo.app.common.extensions.showDialog
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.view.BaseFragment
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import com.siclo.app.databinding.LoginFragmentBinding
import com.siclo.app.ui.login.model.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    private val viewModel: LoginViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun getLayoutRes() = R.layout.login_fragment

    override fun getInternalViewModel() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescription.setText(R.string.login_credentials_enter)
        btnBackAction.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onStart() {
        super.onStart()
        if (!isRestoredFromBackStack) {
            viewModel.loginForm.clearForm()
        }
    }

    override fun handleEvent(event: BaseViewEvent) {
        super.handleEvent(event)
        when (event) {
            is BaseViewEvent.OnRequestEvent -> {
                handleLoginRequest(event.resource)
            }
        }
    }

    private fun handleLoginRequest(event: Resource<*>) {
        if (event.status == Resource.SUCCESS) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        } else if (event.status == Resource.ERROR) {
            showDialog("Error result: ${event.message}")
        }
    }
}