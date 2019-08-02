package top.betterramon.weatherapp.domain.datasource

import top.betterramon.weatherapp.domain.model.Forecast
import top.betterramon.weatherapp.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?

}