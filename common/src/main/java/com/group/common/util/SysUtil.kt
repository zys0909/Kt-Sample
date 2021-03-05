package com.group.common.util

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import com.group.common.core.App
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/24 0024
 */
object SysUtil {

    fun getIP() {

    }

    fun getLocalIpAddress(): String {
        return try {
            //获取wifi服务
            val wifiManager = App.instance.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val i = wifiInfo.ipAddress
            Log.i("测试TAG",i.toString())
            int2ip(i)
        } catch (ex: Exception) {
            " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.message
        }
    }

    //GPRS连接下的ip
    fun getIpAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val inf: NetworkInterface = en.nextElement()
                val enumIpAdd: Enumeration<InetAddress> = inf.inetAddresses
                while (enumIpAdd.hasMoreElements()) {
                    val ineptAddress: InetAddress = enumIpAdd.nextElement()
                    if (!ineptAddress.isLoopbackAddress) {
                        return ineptAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            Log.i("WifiPreferenceIpAddress", ex.toString())
        }
        return null
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

}