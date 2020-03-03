package com.uni.pis.data

 class userData {

    companion object  {
        var first_name="test"
        var last_name="test"
        var email=""
        var phoneNumber=""
        var gender=""
        var city="test"
        var birthdate=""
        var image=""

    }
     constructor( first_name: String, last_name:String, email:String, phoneNumber:String , gender:String, city:String, birthdate:String,image: String)
     {
         userData.first_name=first_name
         userData.last_name=last_name
         userData.email=email
         userData.phoneNumber=phoneNumber
         userData.gender=gender
         userData.city=city
         userData.birthdate=birthdate
         userData.image=image



     }
}