package com.uni.pis.Adapter



import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.uni.pis.R
import com.uni.pis.data.Message
import java.util.*



@Suppress("NAME_SHADOWING")
class MessageAdapter(var context: Context) :
    BaseAdapter() {
    var messages: MutableList<Message> = ArrayList()
    fun add(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(position: Int): Any {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }




    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        var convertView = convertView
        val holder = MessageViewHolder()
        val messageInflater =
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val message = messages[position]
        if (message.isBelongsToCurrentUser) {
            convertView = messageInflater.inflate(R.layout.my_message, null)
            holder.messageBody =
                convertView.findViewById<View>(R.id.message_body) as TextView
            convertView.tag = holder
            holder.messageBody!!.text = message.text
        } else {
            convertView = messageInflater.inflate(R.layout.their_message, null)
            holder.name = convertView.findViewById<View>(R.id.name) as TextView
            holder.messageBody =
                convertView.findViewById<View>(R.id.message_body) as TextView
            convertView.tag = holder
            holder.name!!.text = message.text
            holder.messageBody!!.text = message.text
        }
        return convertView
    }

}


internal class MessageViewHolder {
    var name: TextView? = null
    var messageBody: TextView? = null
}
