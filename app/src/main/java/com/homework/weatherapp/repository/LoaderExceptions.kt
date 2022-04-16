package com.homework.weatherapp.repository

class LoaderExceptions {

    private val serverside = 500..599
    private val clientside = 400..499
    private val redirection = 300..399
    private val serverMessage1: String = "Ошибка на стороне сервера"
    private val serverMessage2: String = "Ошибка на стороне клиента"
    private val serverMessage3: String = "Сработало перенаправление"

    fun check(code: Int)  =
        when (code) {
            in serverside -> serverMessage3
            in clientside -> serverMessage2
            in redirection -> serverMessage1
            else -> {"Что то пошло не так"}
        }
    }
