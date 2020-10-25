package com.ragalik.telegram.ui.fragment

import androidx.fragment.app.Fragment
import com.ragalik.telegram.R
import com.ragalik.telegram.util.APP_ACTIVITY
import com.ragalik.telegram.util.hideKeyboard

class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }
}