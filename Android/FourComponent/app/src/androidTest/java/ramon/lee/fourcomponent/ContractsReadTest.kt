package ramon.lee.fourcomponent

import android.content.ContentProviderOperation
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/12 23:58
 */
@RunWith(AndroidJUnit4::class)
class ContractsReadTest {
    companion object {
        private const val TAG = "ContractsReadTest"

        // content://com.android.contacts/contacts
        private val CONTRACTS_URL: Uri = ContactsContract.Contacts.CONTENT_URI

        // content://com.android.contacts/data/phones
        private val PHONES_URL: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // content://com.android.contacts/data/emails
        private val EMAIL_URI: Uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI

        private val _ID = ContactsContract.Contacts._ID
        private val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        private val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
        private val CONTACT_ID = ContactsContract.Data.CONTACT_ID

        private val PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        private val PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE
        private val EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA
        private val EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE
    }

    @Test
    fun testReadContracts() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val contentResolver = appContext.contentResolver
        val c = contentResolver.query(CONTRACTS_URL, null, null, null, null)
        c?.let { cursor ->
            while (c.moveToNext()) {
                val _id = cursor.getInt(cursor.getColumnIndex(_ID))
                val displayName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))

                Log.i(TAG, "display name：$displayName")

                // 电话和 email，一个人可能对应多个
                val phones = arrayListOf<String>()
                val emails = arrayListOf<String>()

                // where clause
                val seletion = "$CONTACT_ID=$_id"

                // 获取手机号
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER))
                if (hasPhoneNumber > 0) {
                    val phoneCursor = contentResolver.query(PHONES_URL, null, seletion, null, null)
                    phoneCursor?.let { pCursor ->
                        while (pCursor.moveToNext()) {
                            val phoneNumber = pCursor.getString(pCursor.getColumnIndex(PHONE_NUMBER))
                            val phoneType = pCursor.getInt(pCursor.getColumnIndex(PHONE_TYPE))
                            // 将联系人添加到列表
                            phones.add("${getPhoneTypeNameById(phoneType)}: $phoneNumber")
                        }
                        pCursor.close()
                    }

                    Log.i(TAG, "phones = $phones")
                }

                // 获取邮箱
                val emCursor = contentResolver.query(EMAIL_URI, null, seletion, null, null)
                emCursor?.let {emailCursor ->
                    while (emailCursor.moveToNext()) {
                        val emailData = emailCursor.getString(emailCursor.getColumnIndex(EMAIL_DATA))
                        val emailType = emailCursor.getInt(emailCursor.getColumnIndex(EMAIL_TYPE))
                        emails.add("${getEmailTypeNameById(emailType)}: $emailData")
                    }
                    emailCursor.close()
                    Log.i(TAG, "emails = $emails")
                }
            }
            cursor.close()
        }
    }

    private fun getPhoneTypeNameById(typeId: Int): String {
        return when (typeId) {
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "home"
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "mobile"
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "work"
            else -> "none"
        }
    }

    private fun getEmailTypeNameById(typeId: Int): String {
        return when (typeId) {
            ContactsContract.CommonDataKinds.Email.TYPE_HOME -> "home"
            ContactsContract.CommonDataKinds.Email.TYPE_WORK -> "work"
            ContactsContract.CommonDataKinds.Email.TYPE_OTHER -> "other"
            else -> "none"
        }
    }
}