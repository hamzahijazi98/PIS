package com.uni.pis.homefrags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.EvenstList
import com.uni.pis.Events.mFirebaseAuth

import com.uni.pis.R
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.fragment_my__events.*


class My_Events_Frag : Fragment() {

    val arrayListMyEvent = ArrayList<EventsListeItem>()
    val AdapterMyEvent=MyEventListAdapter(arrayListMyEvent)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_my__events, container, false)
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            var userID= mFirebaseAuth.currentUser?.uid!!
            var data = BackgroundWorker(view.context)
            data.execute("myevents",userID )
        }
        catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }
    }
}
