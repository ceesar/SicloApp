package com.siclo.app.ui.signup.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.siclo.app.R
import com.siclo.app.common.extensions.showDialog
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.view.BaseFragment
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import com.siclo.app.databinding.SignUpUserFragmentBinding
import com.siclo.app.ui.signup.model.SignUpUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar.*

@AndroidEntryPoint
class SignUpUserFragment : BaseFragment<SignUpUserFragmentBinding>() {

    private val args: SignUpUserFragmentArgs by navArgs()

    private val viewModel: SignUpUserViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun getLayoutRes() = R.layout.sign_up_user_fragment

    override fun getInternalViewModel() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.emailFields = args.emailFields
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescription.setText(R.string.register)
        btnBackAction.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onStart() {
        super.onStart()
        if (!isRestoredFromBackStack) {
            viewModel.userForm.clearForm()
        }
    }

    override fun handleEvent(event: BaseViewEvent) {
        super.handleEvent(event)
        when (event) {
            is BaseViewEvent.OnRequestEvent -> handleRegisterRequest(event.resource)
        }
    }

    private fun handleRegisterRequest(event: Resource<*>) {
        if (event.status == Resource.SUCCESS) {
            findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
        } else if (event.status == Resource.ERROR) {
            showDialog("Error result: ${event.message}")
        }
    }
}