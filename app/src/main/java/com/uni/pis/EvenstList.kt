package com.uni.pis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.activity_evenst_list.*


class EvenstList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evenst_list)

        val arrayListMyEve=ArrayList<EventsListeItem>()
        val arrayListMyInv=ArrayList<EventsListeItem>()
        arrayListMyEve.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))
        arrayListMyEve.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))



        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))
        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))
        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))

        val eventAdapter=MyEventListAdapter(arrayListMyEve,this)
        rv_invevents.layoutManager=LinearLayoutManager(this)
        rv_invevents.adapter=eventAdapter

        val event2Adapter=MyEventListAdapter(arrayListMyInv,this)
        rv_Myeven.layoutManager=LinearLayoutManager(this)
        rv_Myeven.adapter=eventAdapter

    }
}
