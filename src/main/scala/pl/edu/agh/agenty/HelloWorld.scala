package pl.edu.agh.agenty

/**
 * Created by oem on 2015-05-14.
 */

import java.net.URL

import com.stackmob.newman._
import com.stackmob.newman.dsl._

import scala.concurrent._
import scala.concurrent.duration._

object HelloWorld {
  def main(args:Array[String]) {
    implicit val httpClient = new ApacheHttpClient
    //execute a GET request
    val url = new URL("http://google.com")
    val response = Await.result(GET(url).apply, 5.second) //this will throw if the response doesn't return within 1 second
    println(s"Response returned from ${url.toString} with code ${response.code}, body ${response.bodyString}")

    //print("Hello world")
  }
}
