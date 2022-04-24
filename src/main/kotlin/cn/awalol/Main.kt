package cn.awalol

import cn.awalol.bean.LearnData
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

val objectMapper = ObjectMapper()
object Main {
    var httpClient = HttpClients.createDefault()
    @JvmStatic
    fun main(args: Array<String>) {
        val userName = args[0]
        val userId = args[1]
        if(userName.contains("|")){//多人
            val userNameList = userName.split("|")
            for(i in 0 .. userNameList.lastIndex){
                val cookie = login(userNameList[i],userId.split("|")[i])
                println(userNameList[i])
                if (cookie=null){
                    continue;
                }
                val learnData = getLearnData(cookie)
                if(learnData.isLearned!!.not()){
                    learnHit(learnData.learnContent!!.id.toString(),cookie)
                }else{
                    println("$i 您已学习过了")
                    //println(userNameList[i])
                }
            }
        }else{ //单人
            val cookie = login(userName,userId)
            val learnData = getLearnData(cookie)
            if(learnData.isLearned!!.not()){
                learnHit(learnData.learnContent!!.id.toString(),cookie)
            }else{
                println("您已经学习过了")
            }
        }
    }

    fun login(userName: String?, userId: String?): String { //登录青年大学习并获取Cookie
        val httpPost = HttpPost("http://qndxx.bestcood.com/mp/WeixinAuth/LoginByUser2.html")
        val nameValuePairList: MutableList<NameValuePair> = ArrayList()
        nameValuePairList.add(BasicNameValuePair("userName", userName))
        nameValuePairList.add(BasicNameValuePair("userId", userId))
        httpPost.entity = UrlEncodedFormEntity(nameValuePairList, "UTF-8")
        val httpResponse: HttpResponse = httpClient.execute(httpPost)
        println(EntityUtils.toString(httpResponse.entity))
        return httpResponse.getFirstHeader("Set-Cookie").value
    }

    fun getLearnData(cookie: String): LearnData { //获取LearnData
        val httpGet = HttpGet("http://qndxx.bestcood.com/nanning/daxuexi")
        httpGet.setHeader("Cookie",cookie)
        val content = EntityUtils.toString(httpClient.execute(httpGet).entity)
        val learnData = "(?<=var learnData = ).+(?=;)".toRegex().find(content)!!.value
        return objectMapper.readValue(learnData,LearnData::class.java)
    }

    fun learnHit(id : String,cookie : String){ //学习
        val httpPost = HttpPost("http://qndxx.bestcood.com/mp/nanning/DaXueXi/LearnHit.html")
        val nameValuePairList : ArrayList<NameValuePair> = ArrayList()
        nameValuePairList.add(BasicNameValuePair("id",id))
        httpPost.entity = UrlEncodedFormEntity(nameValuePairList,"UTF-8")
        httpPost.setHeader("Cookie",cookie)
        val httpResponse = httpClient.execute(httpPost)
        println(EntityUtils.toString(httpResponse.entity))
    }
}
