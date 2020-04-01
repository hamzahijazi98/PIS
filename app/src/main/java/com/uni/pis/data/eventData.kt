package com.uni.pis.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class eventData (var Event_ID: String, var Inv_No: String, var  Date: String, var Event_type: String,
                 var  StartTime: String,
                 var   EndTime: String, var   InviterID: String, var firstinvitername: String,
                 var secondinvitername: String, var Place_ID: String, var image: String,
                 var Description:String):Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Event_ID)
        parcel.writeString(Inv_No)
        parcel.writeString(Date)
        parcel.writeString(Event_type)
        parcel.writeString(StartTime)
        parcel.writeString(EndTime)
        parcel.writeString(InviterID)
        parcel.writeString(firstinvitername)
        parcel.writeString(secondinvitername)
        parcel.writeString(Place_ID)
        parcel.writeString(image)
        parcel.writeString(Description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<eventData> {
        override fun createFromParcel(parcel: Parcel): eventData {
            return eventData(parcel)
        }

        override fun newArray(size: Int): Array<eventData?> {
            return arrayOfNulls(size)
        }
    }

}