package com.cnq.androidskillhelper.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cnq.androidskillhelper.R
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class MainActivity : AppCompatActivity() {
    private var mNfcAdapter: NfcAdapter? = null
    private var mPendingIntent: PendingIntent? = null
    private lateinit var mIntentFilter: Array<IntentFilter>
    private lateinit var mTechList: Array<Array<String>>

    // 卡片返回来的正确信号
    private val SELECT_OK = stringToBytes("1000")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcCheck()

        init()
    }
    /**
     * 关闭检测
     */
    override fun onPause() {
        super.onPause()
        if (mNfcAdapter == null) return
        mNfcAdapter!!.disableForegroundDispatch(this)
    }

    /**
     * 开启检测,检测到卡后，onNewIntent() 执行
     * enableForegroundDispatch()只能在onResume() 方法中，否则会报：
     * Foreground dispatch can only be enabled when your activity is resumed
     */
    override fun onResume() {
        super.onResume()
        if (mNfcAdapter == null) return
        mNfcAdapter!!.enableForegroundDispatch(this, mPendingIntent, mIntentFilter, mTechList)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("test","检测到卡")
        // IsoDep卡片通信的工具类，Tag就是卡
        val isoDep = IsoDep.get(intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag)
        if (isoDep == null) {
            val info = "读取卡信息失败"
            toast(info)
            return
        }
        try { // NFC与卡进行连接
            isoDep.connect()
            val AID = "F123466666"
            //转换指令为byte[]
            val command = buildSelectApdu(AID)
            // 发送指令
            val result = isoDep.transceive(command)
            // 截取响应数据
            val resultLength = result.size
            val statusWord = byteArrayOf(result[resultLength - 2], result[resultLength - 1])
            val payload: ByteArray = Arrays.copyOf(result, resultLength - 2)
            // 检验响应数据
            if (Arrays.equals(SELECT_OK, statusWord)) {
                val accountNumber = String(payload, Charset.forName("UTF-8"))
                Log.e("nfctest", "----> $accountNumber")
            } else {
                val info = bytesToString(result)
                Log.e("nfctest", "----> error$info")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    /**
     * 初始化
     */
    private fun init() { // NFCActivity 一般设置为: SingleTop模式 ，并且锁死竖屏，以避免屏幕旋转Intent丢失
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        // 私有的请求码
        val REQUEST_CODE = 1 shl 16
        val FLAG = 0
        mPendingIntent = PendingIntent.getActivity(this@MainActivity, REQUEST_CODE, intent, FLAG)
        // 三种过滤器
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val tech = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        val tag = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        mIntentFilter = arrayOf(ndef, tech, tag)
        // 只针对ACTION_TECH_DISCOVERED
        mTechList = arrayOf(arrayOf(IsoDep::class.java.name), arrayOf(NfcA::class.java.name), arrayOf(NfcB::class.java.name), arrayOf(NfcV::class.java.name), arrayOf(NfcF::class.java.name), arrayOf(Ndef::class.java.name))
    }
    /**
     * 检测是否支持 NFC
     */
    private fun nfcCheck() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            val info = "手机不支付NFC功能"
            toast(info)
            return
        }
        if (!mNfcAdapter!!.isEnabled) {
            val info = "手机NFC功能没有打开"
            toast(info)
            val setNfc = Intent(Settings.ACTION_NFC_SETTINGS)
            startActivity(setNfc)
        } else {
            val info = "手机NFC功能正常"
            toast(info)
        }
    }

    private fun stringToBytes(s: String): ByteArray? {
        val len = s.length
        require(len % 2 != 1) { "指令字符串长度必须为偶数 !!!" }
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character
                    .digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    private fun bytesToString(data: ByteArray): String? {
        val sb = StringBuilder()
        for (d in data) {
            sb.append(String.format("%02X", d))
        }
        return sb.toString()
    }


    private fun buildSelectApdu(aid: String): ByteArray? {
        val HEADER = "00A40400"
        return stringToBytes(HEADER + String.format("%02X", aid.length / 2) + aid)
    }

    private fun toast(info: String) {
        Toast.makeText(this@MainActivity, info, Toast.LENGTH_SHORT).show()
    }
}
