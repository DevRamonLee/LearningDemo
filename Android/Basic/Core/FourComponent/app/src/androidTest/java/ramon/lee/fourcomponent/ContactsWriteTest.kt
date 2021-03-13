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
 * @Desc : 在 ContentProvider 插入联系人
 * @Author : Ramon
 * @create 2021/3/13 23:42
 */
@RunWith(AndroidJUnit4::class)
class ContactsWriteTest {
    companion object {
        private const val TAG = "ContactsWriteTest"

        // content://com.android.contacts/raw_contacts
        private val RAW_CONTACTS_URI: Uri = ContactsContract.RawContacts.CONTENT_URI
        // content://com.android.contacts/data
        private val DATA_URI = ContactsContract.Data.CONTENT_URI

        private const val ACCOUNT_TYPE = ContactsContract.RawContacts.ACCOUNT_TYPE
        private const val ACCOUNT_NAME = ContactsContract.RawContacts.ACCOUNT_NAME

        private const val RAW_CONTACT_ID = ContactsContract.Data.RAW_CONTACT_ID
        private const val MIMETYPE = ContactsContract.Data.MIMETYPE

        private const val NAME_ITEM_TYPE =
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
        private const val DISPLAY_NAME =
            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME

        private const val PHONE_ITEM_TYPE =
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        private const val PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        private const val PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE
        private const val PHONE_TYPE_HOME = ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        private const val PHONE_TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE

        private const val EMAIL_ITEM_TYPE =
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        private const val EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA
        private const val EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE
        private const val EMAIL_TYPE_HOME = ContactsContract.CommonDataKinds.Email.TYPE_HOME
        private const val EMAIL_TYPE_WORK = ContactsContract.CommonDataKinds.Email.TYPE_WORK
        private const val AUTHORITY = ContactsContract.AUTHORITY

    }

    @Test
    fun testWriteContact() {
        val operations = arrayListOf<ContentProviderOperation>()

        var operation = ContentProviderOperation.newInsert(RAW_CONTACTS_URI)
            .withValue(ACCOUNT_TYPE, null)
            .withValue(ACCOUNT_NAME, null)
            .build()

        operations.add(operation)

        // 添加联系人名称操作
        operation = ContentProviderOperation.newInsert(DATA_URI)
            .withValueBackReference(RAW_CONTACT_ID, 0)
            .withValue(MIMETYPE, NAME_ITEM_TYPE)
            .withValue(DISPLAY_NAME, "Ramon Lee")
            .build()

        operations.add(operation)

        // 添加家庭座机号码
        operation = ContentProviderOperation.newInsert(DATA_URI)
            .withValueBackReference(RAW_CONTACT_ID, 0)
            .withValue(MIMETYPE, PHONE_ITEM_TYPE)
            .withValue(PHONE_TYPE, PHONE_TYPE_HOME)
            .withValue(PHONE_NUMBER, "3360075")
            .build()

        operations.add(operation)

        // 添加移动手机号码
        operation = ContentProviderOperation.newInsert(DATA_URI)
            .withValueBackReference(RAW_CONTACT_ID, 0)
            .withValue(MIMETYPE, PHONE_ITEM_TYPE)
            .withValue(PHONE_TYPE, PHONE_TYPE_MOBILE)
            .withValue(PHONE_NUMBER, "15900962200")
            .build()
        operations.add(operation)

        // 添加家庭邮箱
        operation = ContentProviderOperation.newInsert(DATA_URI)
            .withValueBackReference(RAW_CONTACT_ID, 0)
            .withValue(MIMETYPE, EMAIL_ITEM_TYPE)
            .withValue(EMAIL_TYPE, EMAIL_TYPE_HOME)
            .withValue(EMAIL_DATA, "xxxx.gmail.com")
            .build()

        operations.add(operation)

        // 添加工作邮箱
        operation = ContentProviderOperation.newInsert(DATA_URI)
            .withValueBackReference(RAW_CONTACT_ID, 0)
            .withValue(MIMETYPE, EMAIL_ITEM_TYPE)
            .withValue(EMAIL_TYPE, EMAIL_TYPE_WORK)
            .withValue(EMAIL_DATA, "xxxx.ten.com")
            .build()

        operations.add(operation)

        val resolver = InstrumentationRegistry.getInstrumentation().targetContext.contentResolver
        // 批量执行，返回结果
        val results = resolver.applyBatch(AUTHORITY, operations)
        for (i in results.indices) {
            Log.i(TAG, "result $i = ${results[i]}")
        }
    }
}