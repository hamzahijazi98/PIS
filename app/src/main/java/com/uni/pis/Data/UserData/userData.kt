package com.uni.pis.Data.UserData

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
         Companion.first_name =first_name
         Companion.last_name =last_name
         Companion.email =email
         Companion.phoneNumber =phoneNumber
         Companion.gender =gender
         Companion.city =city
         Companion.birthdate =birthdate
         Companion.image =image



     }
}