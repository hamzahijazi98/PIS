package com.uni.pis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.EventsAdapter.EventsAdapter
import com.uni.pis.Data.EventData.Events_Item
import kotlinx.android.synthetic.main.fragment_create_event_.*

class Events_Frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_event_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val EventsTypeArrayList=ArrayList<Events_Item>()
        EventsTypeArrayList.add(Events_Item(getString( R.string.wedding), R.drawable.ic_wedding))
        EventsTypeArrayList.add(Events_Item(getString( R.string.birthday), R.drawable.ic_wedding))
        EventsTypeArrayList.add(Events_Item(getString( R.string.Graduation), R.drawable.ic_wedding))
        EventsTypeArrayList.add(Events_Item(getString( R.string.Engagement), R.drawable.ic_wedding))

        val myadapter = context?.let { EventsAdapter(EventsTypeArrayList, it) }
        RV_events.layoutManager= LinearLayoutManager(context)
        RV_events.adapter=myadapter

    }



}