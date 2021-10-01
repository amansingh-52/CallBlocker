package com.example.callblocker.ui

import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callblocker.R
import com.example.callblocker.adapter.BlockListAdapter
import com.example.callblocker.db.blocklist.PhoneEntry
import com.example.callblocker.utils.Constants.SELECT_PHONE_NUMBER
import com.example.callblocker.viewmodel.EntryViewModel
import com.google.android.material.textfield.TextInputEditText


class AddToBlockList : AppCompatActivity(), BlockListAdapter.OnClick {

    lateinit var list: ArrayList<PhoneEntry>
    lateinit var numberList: ArrayList<String>
    private lateinit var adapters: BlockListAdapter
    private lateinit var viewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_block_list)
        list = ArrayList()
        numberList = ArrayList()
        adapters = BlockListAdapter(this, this, list)
        setUpUI()
        setUpRV()
        initObservers()
        setUpOnClick()
    }

    private fun setUpOnClick() {

        val add = findViewById<Button>(R.id.add_button)
        val openContacts = findViewById<Button>(R.id.choose_from_contact)
        val textInputEditText = findViewById<TextInputEditText>(R.id.textInputEditText)

        add.setOnClickListener {
            if (textInputEditText.text.toString().trim().isNotEmpty() && insertNumberInDB(
                    textInputEditText.text.toString()
                )
            )
                textInputEditText.text = null
        }

        openContacts.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(i, SELECT_PHONE_NUMBER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {

            val contactUri: Uri? = data?.data
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val cursor: Cursor? = contactUri?.let {
                applicationContext.contentResolver.query(
                    it,
                    projection,
                    null,
                    null,
                    null
                )
            }

            if (cursor!!.moveToFirst()) {
                val numberIndex: Int =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number: String = cursor.getString(numberIndex)
                insertNumberInDB(number)
            }

            cursor.close()
        }
    }

    private fun insertNumberInDB(number: String): Boolean {

        var num = number
        num = num.replace(" ", "")
        if (num[0] == '0')
            num = number.substring(0)
        if (!num.contains("+"))
            num = "+91$num"

        if (isValidMobile(num)) {
            if (!numberList.contains(num)) {
                viewModel.insert(PhoneEntry(num))
                return true
            } else {
                Toast.makeText(this, "Number already exists", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun initObservers() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(EntryViewModel(application)::class.java)

        viewModel.getNumbers.observe(this) {
            numberList.clear()
            numberList.addAll(it)
        }

        viewModel.allBlockedNumber.observe(this) {
            list.clear()
            list.addAll(it)
            adapters.updateList(it)
        }
    }

    private fun setUpRV() {
        val blocklistRV = findViewById<RecyclerView>(R.id.blocklist_rv)
        blocklistRV.adapter = adapters
        blocklistRV.hasFixedSize()
        blocklistRV.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.primaryDarkColor)))
        }
    }

    private fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    override fun onCLick(phoneEntry: PhoneEntry) {
        viewModel.delete(phoneEntry)
        Toast.makeText(this, "Deleted " + phoneEntry.number, Toast.LENGTH_SHORT).show()
    }
}