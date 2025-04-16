package ramon.lee.fourcomponent.activity

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityProviderTestBinding
import ramon.lee.fourcomponent.databinding.ItemPersonInfoBinding
import ramon.lee.fourcomponent.provider.Person
import ramon.lee.fourcomponent.provider.PersonObserver


class ProviderTestActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProviderTestActivity"
        private const val AUTHORITY = "ramon.lee.PersonProvider"
        private val PERSON_ALL_URI: Uri = Uri.parse("content://$AUTHORITY/persons")
    }

    private var binding: ActivityProviderTestBinding? = null
    private var resolver: ContentResolver? = null
    private var personAdapter: PersonAdapter? = null
    private var obserable: PersonObserver? = null

    private var persons = arrayListOf<Person>()

    private val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            query()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_provider_test)
        resolver = contentResolver
        personAdapter = PersonAdapter(persons)
        binding?.let {
            it.recycler.apply {
                layoutManager = LinearLayoutManager(this@ProviderTestActivity)
                adapter = personAdapter
            }
        }
        binding?.onClick = ClickAdapter()
        obserable = PersonObserver(handler)
        resolver?.registerContentObserver(PERSON_ALL_URI, true, obserable!!)
    }

    /**
     * 初始化一些数据
     */
    private fun init() {
        val persons = ArrayList<Person>()
        val person1 = Person(name = "Rick",age =  22, info = "gender")
        val person2 = Person(name = "Leo",age =  21, info = "actor")
        val person3 = Person(name = "Luke",age =  20, info = "student")

        persons.add(person1)
        persons.add(person2)
        persons.add(person3)

        for (i in persons.indices) {
            val value = ContentValues()
            value.put("name", persons[i].name)
            value.put("age", persons[i].age)
            value.put("info", persons[i].info)
            resolver?.insert(PERSON_ALL_URI, value)
        }
    }

    /**
     * 查询所有记录
     */
    private fun query() {
        val c: Cursor? = resolver?.query(PERSON_ALL_URI, null, null, null, null)
        val results = arrayListOf<Person>()
        c?.let {
            while (c.moveToNext()) {
                val _id = c.getInt(c.getColumnIndex("_id"))
                val name = c.getString(c.getColumnIndex("name"))
                val age = c.getInt(c.getColumnIndex("age"))
                val info = c.getString(c.getColumnIndex("info"))
                results.add(Person(_id, name, age, info))
            }
            c.close()
            persons.clear()
            persons.addAll(results)
            personAdapter?.notifyDataSetChanged()
        } ?: run {
            Log.i(TAG, "cursor is null")
        }
    }

    /**
     * 插入一条记录
     */
    private fun insert() {
        val person = Person(name = "Anna", age = 18, info = "princess")
        val value = ContentValues()
        value.put("name", person.name)
        value.put("age", person.age)
        value.put("info", person.info)
        resolver?.insert(PERSON_ALL_URI, value)
    }

    /**
     * 更新一条记录
     */
    private fun update() {
        // 将指定 name 的 age 更新为 30
        val value = ContentValues()
        value.put("age", 30)
        resolver?.update(PERSON_ALL_URI, value, "name = ?", arrayOf("Luke"))
    }

    /**
     * 删除一条记录
     */
    private fun delete() {
        val delUri = ContentUris.withAppendedId(PERSON_ALL_URI, 1)
        resolver?.delete(delUri, null, null)
    }

    override fun onDestroy() {
        resolver?.unregisterContentObserver(obserable!!)
        super.onDestroy()
    }

    inner class ClickAdapter {
        fun click(view: View) {
            when (view.id) {
                R.id.btn_init -> init()
                R.id.btn_query -> query()
                R.id.btn_insert -> insert()
                R.id.btn_update -> update()
                R.id.btn_delete -> delete()
            }
        }
    }

    class PersonAdapter(private val items: List<Person>): RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_person_info, parent, false))
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        inner class ViewHolder(val binding: ItemPersonInfoBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(person: Person) {
                binding.person = person
                binding.executePendingBindings()
            }
        }
    }
}