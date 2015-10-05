package pl.edu.agh.agenty

/**
 * Created by oem on 2015-05-14.
 */

import java.net.URL

import com.stackmob.newman._
import com.stackmob.newman.dsl._
import com.stackmob.newman.response.HttpResponse

import scala.concurrent._
import scala.concurrent.duration._

object HelloWorld {
  def main(args:Array[String]) {
    implicit val httpClient = new ApacheHttpClient
    //execute a GET request
    var xmlParameter="/format,xml"
    val url = new URL("http://a.wykop.pl/user/login"+xmlParameter)
    var response = Await.result(POST(url).addHeaders(("Content-Type", "application/x-www-form-urlencoded")).addBody("login=zle_lico&password=na_agenty").apply, 5.second) //this will throw if the response doesn't return within 5 sec
    println(s"Response returned from ${url.toString} with code ${response.code}, body ${response.bodyString}")
    response=fetch("user","login",List(),Map("appkey"->"g2DvJJTVWt"),Map("login"->"zle_lico","password"->"na_agenty"))
    println(s"Response returned from ${url.toString} with code ${response.code}, body ${response.bodyString}")


    //print("Hello world")
  }

  def fetch(resourceName:String,methodName:String,methodParams:List[String],apiParams:Map[String,String],postParams:Map[String,String]): HttpResponse = {
    implicit val httpClient = new ApacheHttpClient

    var urlString="http://a.wykop.pl/"+resourceName+"/"+methodName
    //could've been written better
    if(methodParams.nonEmpty) {
      urlString+="/"
      for (l <- methodParams) urlString += "/" + l
    }
    //if(apiParams.nonEmpty) {
      urlString+="/format,xml,"
      for((k,v)<-apiParams) urlString+=k+","+v+","
      urlString.stripSuffix(",")
    //}
    val post=POST(new URL(urlString)).addHeaders(("Content-Type", "application/x-www-form-urlencoded"))
    var postString=""
    if(apiParams.nonEmpty) {
      for ((k, v) <- postParams) postString += k + "=" + v + "&"
      postString.stripSuffix("&")
    }
    val url=new URL("http://a.wykop.pl/"+resourceName+"/"+methodName)
    val response=Await.result(post.addBody(postString).apply,5.second)
    response
  }
  //def main
}
