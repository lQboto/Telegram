package com.ragalik.telegram.ui.fragments

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.ragalik.telegram.MainActivity
import com.ragalik.telegram.R
import com.ragalik.telegram.activities.RegisterActivity
import com.ragalik.telegram.utilits.AUTH
import com.ragalik.telegram.utilits.AppTextWatcher
import com.ragalik.telegram.utilits.replaceActivity
import com.ragalik.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*


class EnterCodeFragment(val mPhoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {

    private lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else {
                showToast(it.exception?.message.toString())
            }
        }
    }
}