package com.hadeer.schoolapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel : LoginViewModal by viewModels()
    private var selectedRole : String? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
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
        handleListener()
        handleBtnEnability(false)
        binding.loginActionBtnInclude.actionBtn.setOnClickListener { loginAction() }
    }

    private fun loginAction() {
        val email = binding.userEmailTextFieldInclude.textFieldInputDataEdt.text.toString()
        val password = binding.userPasswordTextFieldInclude.secureTextFieldInputDataEdt.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty() && selectedRole != null){
            viewModel.loginUser(
                email = email,
                password = password,
                role = selectedRole!!
            )
        }
        }


    private fun handleViewContent() {
        binding.userEmailTextFieldInclude.textFieldTitleTv.text = getString(R.string.user_email)
        binding.userPasswordTextFieldInclude.textFieldTitleTv.text =
            getString(R.string.user_password)

        binding.userEmailTextFieldInclude.textFieldInputDataEdt.hint =
            getString(R.string.your_email_gmail_com_hint)
        binding.userPasswordTextFieldInclude.secureTextFieldInputDataEdt.hint =
            getString(R.string.your_password_hint)

        handleBtnEnability(binding.userEmailTextFieldInclude.textFieldInputDataEdt.text.toString().isNotEmpty())
        binding.loginActionBtnInclude.actionBtn.text = getString(R.string.login_btn_txt)

        binding.radioSelectionRoleInclude.radioContainerGroup.setOnCheckedChangeListener{ group, checkedId ->
            val checkItem = group.findViewById<AppCompatRadioButton>(checkedId)
            selectedRole = checkItem.text.toString()
        }
    }

    private fun handleBtnEnability(enable : Boolean) {
        binding.loginActionBtnInclude.actionBtn.isEnabled = true
        if(enable){
            binding.loginActionBtnInclude.actionBtn.background = resources.getDrawable(R.drawable.main_active_btn_bg)
            binding.loginActionBtnInclude.actionBtn.setTextColor(resources.getColor(R.color.white))
        }else{
            binding.loginActionBtnInclude.actionBtn.background = resources.getDrawable(R.drawable.main_inactive_btn_bg)
            binding.loginActionBtnInclude.actionBtn.setTextColor(resources.getColor(R.color.dark_blue))
        }
    }

    private fun handleListener(){
        coroutineScope.launch {
            viewModel.loginIntent.collect{
                when(it){
                    is LoginIntent.Ideal -> {
                        isLoading(it.state.isLoading)
                        handleBtnEnability(it.state.isLoginBtnEnabled)
                    }
                    is LoginIntent.InputFailed ->{
                        isLoading(it.state.isLoading)
                        handleBtnEnability(it.state.isLoginBtnEnabled)
                        if(it.state.emailErrorText != ""){
                            handleErrorInput(
                                binding.userEmailTextFieldInclude.textFieldInputDataEdt,
                                binding.userEmailTextFieldInclude.textFieldInputLy,
                                it.state.emailErrorText
                            )
                        }
                        if(it.state.passwordErrorString != ""){
                            handleErrorInput(
                                binding.userPasswordTextFieldInclude.secureTextFieldInputDataEdt,
                                binding.userPasswordTextFieldInclude.secureTextFieldInputLy,
                                it.state.passwordErrorString
                            )
                        }
                        if(it.state.roleErrorString != ""){
                            binding.radioSelectionRoleInclude.radioContainerGroup.setBackgroundResource(
                                R.drawable.edit_text_error
                            )
                        }
                    }
                    is LoginIntent.Success -> {
                        handleBtnEnability(it.state.isLoginBtnEnabled)
                        if(it.state.isLoginSuccess){
                            Toast.makeText(requireContext(), "Success login!!" , Toast.LENGTH_LONG).show()
                        }
                    }
                    is LoginIntent.Failed -> {
                        handleBtnEnability(it.state.isLoginBtnEnabled)
                        handleFailedLogin(it.state.loginApiError)
                        isLoading(it.state.isLoading)
                    }
                }
            }
        }
    }

    private fun isLoading(load : Boolean){
        if(load){
            binding.progressBarLy.visibility = View.VISIBLE
        }
        else{
            binding.progressBarLy.visibility = View.GONE
        }
    }

    private fun handleErrorInput(inputField : TextInputEditText, inputLayout : TextInputLayout, error : String){
        inputField.error = error
        inputLayout.setBackgroundResource(R.drawable.edit_text_error)
    }

    private fun handleFailedLogin(error : String){
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}