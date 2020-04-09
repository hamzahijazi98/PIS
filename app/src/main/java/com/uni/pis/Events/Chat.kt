package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.sendbird.android.*
import com.uni.pis.Adapter.MessageAdapter
import com.uni.pis.R
import com.uni.pis.data.Message
import com.uni.pis.data.userData
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList

class Chat : AppCompatActivity() {
    private val SENDBIRDAPPID="C70ACBE6-0911-45D5-B02B-C56D3ADDF158"
    private var CHANNEL_URL = ""
    private var messageadapter: MessageAdapter? = null
    private var listViewMessages: ListView? = null
    private var msgText: String? = null
    private val userid=mFirebaseAuth.currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageadapter = MessageAdapter(this)
        listViewMessages = findViewById<View>(R.id.messages_view) as ListView
        listViewMessages!!.adapter = messageadapter

        SendBird.init(SENDBIRDAPPID, this)

            SendBird.connect(userid, object : SendBird.ConnectHandler { override fun onConnected(user: User?, e: SendBirdException?) {
                if (e != null)
                {
                    return
                }
            } })
        SendBird.setChannelInvitationPreference(true, object : SendBird.SetChannelInvitationPreferenceHandler {
            override fun onResult(e: SendBirdException?) {
                if (e != null) { // Error.
                    return
                }
            }
        })

        if(intent.hasExtra("ChannelUrl")) {
            CHANNEL_URL = intent.extras!!.get("ChannelUrl").toString()



          GroupChannel.getChannel(CHANNEL_URL, object : GroupChannel.GroupChannelGetHandler {
                override fun onResult(groupChannel: GroupChannel, e: SendBirdException?) {
                    if (e != null) { // Error.
                        return
                    }
                    // There should only be one single instance per channel view.
                    val prevMessageListQuery: PreviousMessageListQuery =
                        groupChannel.createPreviousMessageListQuery()
                    prevMessageListQuery.setIncludeMetaArray(true) // Retrieve a list of messages along with their metaarrays.
                    prevMessageListQuery.setIncludeReactions(true) // Retrieve a list of messages along with their reactions.
                    // Retrieving previous messages.
                    prevMessageListQuery.load(
                        30,
                        true,
                        object : MessageListQuery.MessageListQueryResult,
                            PreviousMessageListQuery.MessageListQueryResult {
                            override fun onResult(
                                messages: List<BaseMessage>,
                                e: SendBirdException?
                            ) {
                                if (e != null) { // Error.
                                    return
                                }
                                var flag = false
                                for (x in messages) {
                                    flag = false
                                    if (x.sender.userId == SendBird.getCurrentUser().userId) flag =
                                        true
                                    val msg = Message(x.message, flag,x.data)
                                    messageadapter!!.add(msg)
                                    listViewMessages!!.setSelection(listViewMessages!!.count - 1)
                                }
                            }
                        })
                }
            })



        btn_send.setOnClickListener {
            GroupChannel.getChannel(CHANNEL_URL, object : GroupChannel.GroupChannelGetHandler {
                override fun onResult(groupChannel: GroupChannel, e: SendBirdException?) {
                    if (e != null) { // Error.
                        return
                    }
                    msgText=et_msg.text.toString()
                    val params: UserMessageParams = UserMessageParams()
                        .setMessage(msgText)
                        .setData(userData.first_name+" "+userData.last_name)
                    groupChannel.sendUserMessage(params, object : BaseChannel.SendUserMessageHandler {
                        override fun onSent(userMessage: UserMessage, e: SendBirdException?) {
                            if (e != null) { // Error.
                                return
                            }
                            val belongsToCurrentUser: Boolean = userMessage.sender.userId == userid
                            val message = Message(userMessage.message, belongsToCurrentUser,userMessage.data)
                            messageadapter!!.add(message)
                            listViewMessages!!.setSelection(listViewMessages!!.count - 1)
                            et_msg!!.text.clear()
                        }
                    })

                }
            })
        }
        SendBird.addChannelHandler("CHANNEL_HANDLER_GROUP_CHANNEL_LIST", object : SendBird.ChannelHandler() {
            override fun onMessageReceived(channel: BaseChannel?, message: BaseMessage) {
                when (message) {
                    is UserMessage -> {
                        val msg = Message(message.message, false,message.data)
                        messageadapter!!.add(msg)
                        listViewMessages!!.setSelection(listViewMessages!!.count - 1)
                    }
                    is FileMessage -> { // ...
                    }
                    is AdminMessage -> { // ...
                    }
                }
            }
        })
    }
    }


}
