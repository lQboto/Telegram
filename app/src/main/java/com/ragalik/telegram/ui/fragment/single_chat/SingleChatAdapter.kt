package com.ragalik.telegram.ui.fragment.single_chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ragalik.telegram.ui.fragment.message_recycler_view.view.MessageView
import com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder.AppHolderFactory
import com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder.HolderImageMessage
import com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder.HolderTextMessage
import com.ragalik.telegram.ui.fragment.message_recycler_view.view_holder.HolderVoiceMessage


class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListMessagesCache = mutableListOf<MessageView>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return mListMessagesCache[position].getTypeView()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderImageMessage -> holder.drawMessageImage(holder, mListMessagesCache[position])
            is HolderTextMessage -> holder.drawMessageText(holder, mListMessagesCache[position])
            is HolderVoiceMessage -> holder.drawMessageVoice(holder, mListMessagesCache[position])
            else -> {}
        }
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    fun addItemToBottom(item: MessageView, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            notifyItemInserted(mListMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: MessageView, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            mListMessagesCache.sortBy { it.timeStamp }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}


