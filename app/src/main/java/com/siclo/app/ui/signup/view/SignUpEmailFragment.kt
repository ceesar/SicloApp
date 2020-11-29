package com.siclo.app.ui.signup.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.siclo.app.R
import com.siclo.app.core.view.BaseFragment
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import com.siclo.app.databinding.SignUpEmailFragmentBinding
import com.siclo.app.ui.signup.model.EmailFields
import com.siclo.app.ui.signup.model.SignUpEmailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar.*

@AndroidEntryPoint
class SignUpEmailFragment : BaseFragment<SignUpEmailFragmentBinding>() {

    private val viewModel: SignUpEmailViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun getLayoutRes() = R.layout.sign_up_email_fragment

    override fun getInternalViewModel() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescription.setText(R.string.register)
        btnBackAction.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onStart() {
        super.onStart()
        if (!isRestoredFromBackStack) {
            viewModel.emailForm.clearForm()
        }
    }

    override fun handleEvent(event: BaseViewEvent) {
        when (event) {
            BaseViewEvent.OnValidationSuccess -> {
                val action = SignUpEmailFragmentDirections
                    .actionSignUpEmailFragmentToSignUpUserFragment(viewModel.emailForm.fields)
                findNavController().navigate(action)
            }
        }
    }
}