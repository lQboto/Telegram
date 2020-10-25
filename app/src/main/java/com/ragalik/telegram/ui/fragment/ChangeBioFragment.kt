package com.ragalik.telegram.ui.fragment

import com.ragalik.telegram.R
import com.ragalik.telegram.database.*
import com.ragalik.telegram.util.*
import kotlinx.android.synthetic.main.fragment_change_bio.*


class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()

        setBioToDatabase(newBio)
    }
}