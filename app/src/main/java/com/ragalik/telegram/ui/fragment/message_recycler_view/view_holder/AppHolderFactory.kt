package com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ragalik.telegram.R
import com.ragalik.telegram.ui.fragment.message_recycler_view.view.MessageView

class AppHolderFactory {

    companion object {
        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                MessageView.MESSAGE_IMAGE -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_image, parent, false)
                    HolderImageMessage(view)
                }
                MessageView.MESSAGE_VOICE -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_voice, parent, false)
                    HolderVoiceMessage(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_text, parent, false)
                    HolderTextMessage(view)
                }
            }
        }
    }

}