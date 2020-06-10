package com.uni.pis.Events

import androidx.test.InstrumentationRegistry
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.EventData.eventData
import org.junit.Test

import org.junit.Assert.*

class EvenstListTest: BackgroundWorker.MyCallback {

    @Test
    fun onResult() {
        var userID = "yoA6ht9zatWI45Yq0DNujTqhMtn1"
        var data = BackgroundWorker( InstrumentationRegistry.getInstrumentation().context)
        data.execute("myevents", userID)

    }

    override fun onResult(result: String?) {
        var type = result!!.split("^")
        if (type.size > 2) {
            if (type[1] == "myevents") {
                var data = type[2].split("*")
                for (i in data) {

                    var friend = i.split("!&")
                    if (friend.size > 1) {
                        var EventID = friend[EvenstList.eventDataOrder.EventID.index].substringAfter("=")
                        var Invitee_No = friend[EvenstList.eventDataOrder.Invitee_No.index].substringAfter("=")
                        var StartTime = friend[EvenstList.eventDataOrder.StartTime.index].substringAfter("=")
                        var EndTime = friend[EvenstList.eventDataOrder.EndTime.index].substringAfter("=")
                        var name = friend[EvenstList.eventDataOrder.EventType.index].substringAfter("=")
                        var Date = friend[EvenstList.eventDataOrder.Date.index].substringAfter("=")
                        var description = friend[EvenstList.eventDataOrder.Description.index].substringAfter("=")
                        var PlaceId = friend[EvenstList.eventDataOrder.PlaceId.index].substringAfter("=")
                        var Image = friend[EvenstList.eventDataOrder.Image.index].substringAfter("=").replace("\\", "")
                        var FirstInviterName =
                            friend[EvenstList.eventDataOrder.FirstInviterName.index].substringAfter("=")
                        var SecondInviterName =
                            friend[EvenstList.eventDataOrder.SecondInviterName.index].substringAfter("=")
                        var InviterId = friend[EvenstList.eventDataOrder.InviterId.index].substringAfter("=")
                        var channelUrl = friend[EvenstList.eventDataOrder.ChannelUrl.index].substringAfter("=")
                        var video = friend[EvenstList.eventDataOrder.Video.index].substringAfter("=").replace("\\", "")

                        var eventdata = eventData(
                            EventID,
                            Invitee_No,
                            Date,
                            name,
                            StartTime,
                            EndTime,
                            InviterId,
                            FirstInviterName,
                            SecondInviterName,
                            PlaceId,
                            Image,
                            description,
                            channelUrl,
                            video

                        )
                        assertEquals(EventID, eventdata.Event_ID)
                        assertEquals(Date, eventdata.Date)
                        assertEquals(name, eventdata.Event_type)
                        assertEquals(StartTime, eventdata.StartTime)
                        assertEquals(EndTime, eventdata.EndTime)
                        assertEquals(InviterId, eventdata.InviterID)

                    }
                }

            }
            if (type[1] == "invitedto") {
                var data = type[2].split("*")

                for (i in data) {

                    var friend = i.split("!&")
                    if (friend.size > 1) {
                        var EventID = friend[EvenstList.eventDataOrder.EventID.index].substringAfter("=")
                        var name = friend[EvenstList.eventDataOrder.EventType.index].substringAfter("=")
                        var description =
                            friend[EvenstList.eventDataOrder.Description.index].substringAfter("=")
                        var Image =
                            friend[EvenstList.eventDataOrder.Image.index].substringAfter("=").replace("\\", "")
                                .trim()
                        var Invitee_No = friend[EvenstList.eventDataOrder.Invitee_No.index].substringAfter("=")
                        var StartTime = friend[EvenstList.eventDataOrder.StartTime.index].substringAfter("=")
                        var EndTime = friend[EvenstList.eventDataOrder.EndTime.index].substringAfter("=")
                        var Date = friend[EvenstList.eventDataOrder.Date.index].substringAfter("=")
                        var PlaceId =
                            friend[EvenstList.eventDataOrder.PlaceId.index].substringAfter("=").substringAfter(
                                "="
                            ) + "&" + friend[EvenstList.eventDataOrder.PlaceId.index + 1]
                        var FirstInviterName =
                            friend[EvenstList.eventDataOrder.FirstInviterName.index].substringAfter("=")
                        var SecondInviterName =
                            friend[EvenstList.eventDataOrder.SecondInviterName.index].substringAfter("=")
                        var InviterId = friend[EvenstList.eventDataOrder.InviterId.index].substringAfter("=")
                        var channelUrl = friend[EvenstList.eventDataOrder.ChannelUrl.index].substringAfter("=")
                        var video = friend[EvenstList.eventDataOrder.Video.index].substringAfter("=")

                        var eventdata = eventData(
                            EventID,
                            Invitee_No,
                            Date,
                            name,
                            StartTime,
                            EndTime,
                            InviterId,
                            FirstInviterName,
                            SecondInviterName,
                            PlaceId,
                            Image,
                            description,
                            channelUrl,
                            video
                        )
                        assertEquals(EventID, eventdata.Event_ID)
                        assertEquals(Date, eventdata.Date)
                        assertEquals(name, eventdata.Event_type)
                        assertEquals(StartTime, eventdata.StartTime)
                        assertEquals(EndTime, eventdata.EndTime)
                        assertEquals(InviterId, eventdata.InviterID)

                    }
                }
            }
        }
    }
}