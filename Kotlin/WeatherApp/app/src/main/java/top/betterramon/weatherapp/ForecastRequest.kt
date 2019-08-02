package top.betterramon.weatherapp

import android.util.Log
import java.net.URL

/**
 * Created by Ramon Lee on 2019/8/2.
 */
class Request(val url: String) {
    public fun run() {
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonStr)
    }
}