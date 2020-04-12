package com.uni.pis.homefrags

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.Adapter.MyInvitedEventsAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth

import com.uni.pis.R
import com.uni.pis.data.eventData
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.fragment_invited__events.*


class Invited_Events_Frag : Fragment() {

    val arrayListMyInvited=ArrayList<eventData>()
    val AdapterInvitedEvent= MyInvitedEventsAdapter(arrayListMyInvited)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         return inflater.inflate(R.layout.fragment_invited__events, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                try {
            var userID = mFirebaseAuth.currentUser?.uid!!
            var data = context?.let { BackgroundWorker(it) }
            data?.execute("invitedtoevent", userID)
            data = context?.let { BackgroundWorker(it) }
        }
        catch (e:Exception){e.message}

    }


}
