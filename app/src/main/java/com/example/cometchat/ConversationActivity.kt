package com.example.cometchat

import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.cometchat.chat.constants.CometChatConstants
import com.cometchat.chat.models.Conversation
import com.cometchat.chat.models.Group
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.conversations.CometChatConversations

class ConversationActivity : AppCompatActivity() {

    private lateinit var conversationsView: CometChatConversations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conversations)

        initView()
        setListeners()
    }

    private fun initView() {
        conversationsView = findViewById(R.id.conversation_view)
    }

    private fun setListeners() {
        conversationsView.setOnItemClick { _, _, conversation ->
            startMessageActivity(conversation)
        }
    }

    private fun startMessageActivity(conversation: Conversation) {
        val intent = Intent(this, MessageActivity::class.java).apply {
            when (conversation.conversationType) {
                CometChatConstants.CONVERSATION_TYPE_GROUP -> {
                    val group = conversation.conversationWith as Group
                    putExtra("guid", group.guid)
                }
                else -> {
                    val user = conversation.conversationWith as User
                    putExtra("uid", user.uid)
                }
            }
        }
        startActivity(intent)
    }
}