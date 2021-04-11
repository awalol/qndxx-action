import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

object Main {
    var httpClient = HttpClients.createDefault()
    @JvmStatic
    fun main(args: Array<String>) {
        val userName = args[0]
        val userId = args[1]
        val cookie = login(userName,userId)
        learnHit(id,cookie)
    }

    fun login(userName: String?, userId: String?): String {
        val httpPost = HttpPost("http://qndxx.bestcood.com/mp/WeixinAuth/LoginByUser2.html")
        val nameValuePairList: MutableList<NameValuePair> = ArrayList()
        nameValuePairList.add(BasicNameValuePair("userName", userName))
        nameValuePairList.add(BasicNameValuePair("userId", userId))
        httpPost.entity = UrlEncodedFormEntity(nameValuePairList, "UTF-8")
        val httpResponse: HttpResponse = httpClient.execute(httpPost)
        return httpResponse.getFirstHeader("Set-Cookie").value
    }

    val id: String
        get() {
            val httpGet = HttpGet("http://qndxx.bestcood.com/nanning/daxuexi")
            val content = EntityUtils.toString(httpClient.execute(httpGet).entity)
            return "(?<=\"id\":).+(?=,\"title\":\")".toRegex().find(content)!!.value
        }

    fun learnHit(id : String,cookie : String){
        val httpPost = HttpPost("http://qndxx.bestcood.com/mp/nanning/DaXueXi/LearnHit.html")
        val nameValuePairList : ArrayList<NameValuePair> = ArrayList()
        nameValuePairList.add(BasicNameValuePair("id",id))
        httpPost.entity = UrlEncodedFormEntity(nameValuePairList,"UTF-8")
        httpPost.setHeader("Cookie",cookie)
        val httpResponse = httpClient.execute(httpPost)
        println(EntityUtils.toString(httpResponse.entity))
    }
}