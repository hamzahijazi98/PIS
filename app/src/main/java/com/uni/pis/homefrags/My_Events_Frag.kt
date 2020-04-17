package com.uni.pis.homefrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uni.pis.Adapter.EventsAdapter.MyEventListAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth

import com.uni.pis.R
import com.uni.pis.Data.EventData.eventData


class My_Events_Frag : Fragment() {

    val MyEventArrayList = ArrayList<eventData>()
    val MyEventArrayListAdapter= MyEventListAdapter(MyEventArrayList)

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
