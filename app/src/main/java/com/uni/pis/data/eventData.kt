package com.uni.pis.data

 class eventData {
     companion object {
         var Event_ID = "test"
         var Inv_No = "test"
         var Date = ""
         var Event_type = ""
         var StartTime = ""
         var EndTime = "test"
         var Description = ""
         var InviterID = ""
         var firstinvitername = ""
         var secondinvitername = ""
         var Place_ID = ""
         var image = ""

     }

     constructor(
         Event_ID: String,
         Inv_No: String,
         Date: String,
         Event_type: String,
         StartTime: String,
         EndTime: String,
         InviterID: String,
         firstinvitername: String,
         secondinvitername: String,
         Place_ID: String,
         image: String
     ) {
         eventData.Date=Date
         eventData.Description= Description
         eventData.EndTime=EndTime
         eventData.Event_ID=Event_ID
         eventData.Event_type=Event_type
         eventData.Inv_No=Inv_No
         eventData.InviterID=InviterID
         eventData.Place_ID=Place_ID
         eventData.StartTime=StartTime
         eventData.secondinvitername=secondinvitername
         eventData.firstinvitername=firstinvitername
         eventData.image=image


     }
 }