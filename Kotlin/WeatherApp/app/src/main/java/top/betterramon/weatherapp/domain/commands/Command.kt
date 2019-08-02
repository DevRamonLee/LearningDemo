package top.betterramon.weatherapp.domain.commands

interface Command<out T> {
    suspend fun execute(): T
}