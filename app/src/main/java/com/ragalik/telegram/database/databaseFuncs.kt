package com.ragalik.telegram.database

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ragalik.telegram.R
import com.ragalik.telegram.model.CommonModel
import com.ragalik.telegram.model.UserModel
import com.ragalik.telegram.util.APP_ACTIVITY
import com.ragalik.telegram.util.AppValueEventListener
import com.ragalik.telegram.util.showToast
import java.util.ArrayList

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putFileToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(UserModel::class.java) ?: UserModel()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })
}

fun updatePhonesToDatabase(arrayContacts: ArrayList<CommonModel>) {
    if (AUTH.currentUser != null) {
        REF_DATABASE_ROOT.child(NODE_PHONES).addListenerForSingleValueEvent(AppValueEventListener {
            it.children.forEach { snaphot ->
                arrayContacts.forEach { contact ->
                    if (snaphot.key == contact.phone) {
                        REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
                            .child(snaphot.value.toString()).child(CHILD_ID)
                            .setValue(snaphot.value.toString())
                            .addOnFailureListener { showToast(it.message.toString()) }

                        REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
                            .child(snaphot.value.toString()).child(CHILD_FULLNAME)
                            .setValue(contact.fullname)
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }
                }
            }
        })
    }
}

fun sendMessage(message: String, receivingUserId: String, typeText: String, function: () -> Unit) {
    val refDialogUser = "$NODE_MESSAGES/$CURRENT_UID/$receivingUserId"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserId/$CURRENT_UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMassage = hashMapOf<String, Any>()
    mapMassage[CHILD_FROM] = CURRENT_UID
    mapMassage[CHILD_TYPE] = typeText
    mapMassage[CHILD_TEXT] = message
    mapMassage[CHILD_ID] = messageKey.toString()
    mapMassage[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMassage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMassage

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun sendMessageAsFile(receivingUserId: String, fileUrl: String, messageKey: String, typeMessage: String) {
    val refDialogUser = "$NODE_MESSAGES/$CURRENT_UID/$receivingUserId"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserId/$CURRENT_UID"

    val mapMassage = hashMapOf<String, Any>()
    mapMassage[CHILD_FROM] = CURRENT_UID
    mapMassage[CHILD_TYPE] = typeMessage
    mapMassage[CHILD_ID] = messageKey
    mapMassage[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP
    mapMassage[CHILD_FILE_URL] = fileUrl

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMassage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMassage

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun updateCurrentUsername(newUsername: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
        .setValue(newUsername)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            deleteOldUsername(newUsername)
        }.addOnFailureListener { showToast(it.message.toString()) }
}

private fun deleteOldUsername(newUsername: String) {
    REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            APP_ACTIVITY.supportFragmentManager.popBackStack()
            USER.username = newUsername
        }.addOnFailureListener { showToast(it.message.toString()) }
}

fun setBioToDatabase(newBio: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            USER.bio = newBio
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }.addOnFailureListener { showToast(it.message.toString()) }
}

fun setNameToDatabase(fullname: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
        .setValue(fullname).addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            USER.fullname = fullname
            APP_ACTIVITY.mAppDrawer.updateHeader()
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }.addOnFailureListener { showToast(it.message.toString()) }
}

fun uploadFileToStorage(uri: Uri, messageKey: String, receivedId: String, typeMessage: String) {
    val path = REF_STORAGE_ROOT.child(FOLDER_FILES).child(messageKey)
    putFileToStorage(uri, path) {
        getUrlFromStorage(path) {
            sendMessageAsFile(receivedId, it, messageKey, typeMessage)
        }
    }
}

fun getMessageKey(id: String) = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    .child(id).push().key.toString()


fun DataSnapshot.getCommonModel(): CommonModel =
    this.getValue(CommonModel::class.java) ?: CommonModel()

fun DataSnapshot.getUserModel(): UserModel =
    this.getValue(UserModel::class.java) ?: UserModel()
