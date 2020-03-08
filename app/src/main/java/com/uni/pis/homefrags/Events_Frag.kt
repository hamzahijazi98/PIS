package com.uni.pis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.EventsAdapter
import com.uni.pis.model.Events_Item
import kotlinx.android.synthetic.main.fragment_events_.*

class Events_Frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_events_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arraylist=ArrayList<Events_Item>()
        arraylist.add(Events_Item("Wedding",R.drawable.weddingbackground))
        arraylist.add(Events_Item("BirthDate",R.drawable.birthdaybackground))
        arraylist.add(Events_Item("Graduation",R.drawable.download))
        arraylist.add(Events_Item("Engagement",R.drawable.download))

        val myadapter = context?.let { EventsAdapter(arraylist,it) }
        RV_events.layoutManager= LinearLayoutManager(context)
        RV_events.adapter=myadapter

    }



}