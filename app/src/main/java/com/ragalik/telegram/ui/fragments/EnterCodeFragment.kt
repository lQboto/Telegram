package com.ragalik.telegram.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ragalik.telegram.R
import kotlinx.android.synthetic.main.fragment_enter_code.*


class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        register_input_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val string = register_input_code.text.toString()
                if (string.length == 6) {
                    verifyCode()
                }
            }

        })
    }

    fun verifyCode () {
        Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
    }
}