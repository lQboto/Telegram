package com.ragalik.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.ragalik.telegram.MainActivity
import com.ragalik.telegram.R
import com.ragalik.telegram.activities.RegisterActivity
import com.ragalik.telegram.util.AUTH
import com.ragalik.telegram.util.USER
import com.ragalik.telegram.util.replaceActivity
import com.ragalik.telegram.util.replaceFragment
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_username.text = USER.username
        settings_btn_change_username.setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }
        settings_btn_change_bio.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}