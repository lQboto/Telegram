package com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ragalik.telegram.database.CURRENT_UID
import com.ragalik.telegram.ui.fragment.message_recycler_view.view.MessageView
import com.ragalik.telegram.util.asTime
import kotlinx.android.synthetic.main.message_item_image.view.*
import kotlinx.android.synthetic.main.message_item_image.view.block_received_image_message
import kotlinx.android.synthetic.main.message_item_image.view.block_user_image_message
import kotlinx.android.synthetic.main.message_item_image.view.chat_received_image_message_time
import kotlinx.android.synthetic.main.message_item_image.view.chat_user_image_message_time
import kotlinx.android.synthetic.main.message_item_voice.view.*

class HolderVoiceMessage(view: View): RecyclerView.ViewHolder(view) {

    val blockReceivedVoiceMessage: ConstraintLayout = view.block_received_voice_message
    val chatReceivedVoiceMessageTime: TextView = view.chat_received_voice_message_time

    val blockUserVoiceMessage: ConstraintLayout = view.block_user_voice_message
    val chatUserVoiceMessageTime: TextView = view.chat_user_voice_message_time

    val chatReceivedBtnPlay: ImageView = view.chat_received_btn_play
    val chatReceivedBtnStop: ImageView = view.chat_received_btn_stop

    val chatUserBtnPlay: ImageView = view.chat_user_btn_play
    val chatUserBtnStop: ImageView = view.chat_user_btn_stop

    fun drawMessageVoice(holder: HolderVoiceMessage, view: MessageView) {
        if (view.from == CURRENT_UID) {
            holder.blockReceivedVoiceMessage.visibility = View.GONE
            holder.blockUserVoiceMessage.visibility = View.VISIBLE
            holder.chatUserVoiceMessageTime.text = view.timeStamp.asTime()
        } else {
            holder.blockReceivedVoiceMessage.visibility = View.VISIBLE
            holder.blockUserVoiceMessage.visibility = View.GONE
            holder.chatReceivedVoiceMessageTime.text = view.timeStamp.asTime()
        }
    }
}