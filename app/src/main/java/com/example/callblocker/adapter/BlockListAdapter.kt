package com.example.callblocker.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.callblocker.R
import com.example.callblocker.db.blocklist.PhoneEntry

class BlockListAdapter(
    private val context: Context,
    private val listener: OnClick,
    private val list: ArrayList<PhoneEntry>
) :
    RecyclerView.Adapter<BlockListAdapter.BlockListViewHolder>() {

    private var selectedPosition = -1
    private var previousSelectedPosition = -1

    inner class BlockListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneNumber: TextView = itemView.findViewById(R.id.number)
        private val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
        private val blockListItem: ConstraintLayout = itemView.findViewById(R.id.phone_item)

        fun onBind(position: Int) {

            phoneNumber.text = list[position].number

            val isSelected = position == selectedPosition

            if (isSelected) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    blockListItem.setBackgroundColor(context.getColor(R.color.primaryDarkColor))
                    deleteButton.visibility = View.VISIBLE
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    blockListItem.setBackgroundColor(context.getColor(R.color.primaryLightColor))
                    deleteButton.visibility = View.GONE
                }
            }
            itemView.isActivated = isSelected

            if (isSelected) previousSelectedPosition = position

            blockListItem.setOnClickListener {
                selectedPosition = if(isSelected) -1 else position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }

            deleteButton.setOnClickListener {
                listener.onCLick(list[position])
                selectedPosition = -1
            }

        }
    }

    interface OnClick {
        fun onCLick(phoneEntry: PhoneEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockListViewHolder =
        BlockListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.blocklist_item, parent, false)
        )

    override fun onBindViewHolder(holder: BlockListViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<PhoneEntry>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}