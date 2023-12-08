package com.example.codoc.model

class LoginModel {
    var username = ""
    var password = ""

    fun loginCheck():Boolean{
        if(username.equals("muttaqien")&&password.equals("samlehoy")){
            return true
        }else{
            return false
        }
    }
}