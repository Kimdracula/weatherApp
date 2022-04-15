package com.homework.weatherapp.repository

class LoaderExceptions {

    private val serverside = 500..599
    private val clientside = 400..499
    private val redirection = 300..399
    private val serverMessage1: String = "Ошибка на стороне сервера"
    private val serverMessage2: String = "Ошибка на стороне клиента"
    private val serverMessage3: String = "Сработало перенаправление"

    fun check(code: Int): String? {
        if (code.equals(serverside)) {
            serverMessage1
        } else if (code.equals(clientside)) {
            serverMessage2
        } else if (code.equals(redirection)) {
            serverMessage3
        } else {
            return serverMessage3
        }
        return null


    }
}