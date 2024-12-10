package com.hadeer.schoolapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleViewContent()
    }

    private fun handleViewContent() {
        binding.userEmailTextFieldInclude.textFieldTitleTv.text = getString(R.string.user_email)
        binding.userPasswordTextFieldInclude.textFieldTitleTv.text =
            getString(R.string.user_password)

        binding.userEmailTextFieldInclude.textFieldInputDataEdt.hint =
            getString(R.string.your_email_gmail_com_hint)
        binding.userPasswordTextFieldInclude.secureTextFieldInputDataEdt.hint =
            getString(R.string.your_password_hint)

        binding.loginActionBtnInclude.actionBtn.text = getString(R.string.login_btn_txt)
    }
}