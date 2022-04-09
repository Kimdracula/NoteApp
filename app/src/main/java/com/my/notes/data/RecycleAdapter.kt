package com.my.notes.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.my.notes.data.RecycleAdapter.NoteViewHolder
import com.my.notes.databinding.RecycleItemBinding
import java.text.SimpleDateFormat
import java.util.*

class RecycleAdapter(private val fragment: Fragment?) : RecyclerView.Adapter<NoteViewHolder>() {
    private var dataSource: DataSource? = null
    private var itemClickListener: OnItemClickListener? = null
    private var lastPosition = -1
    var menuPosition = 0


    fun setDataSource(dataSource: DataSource?) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        dataSource?.getData(position)?.let { holder.bind(it) }
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


    override fun getItemCount(): Int {
        return dataSource!!.size()!!
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    inner class NoteViewHolder(private val binding: RecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(cardData: CardData) {
            with(binding) {
                textViewHeader.text = cardData.header
                textViewDescription.text = cardData.description
                imageView.setImageResource(cardData.picture)
                textViewDate.text = cardData.date?.let {
                    SimpleDateFormat(
                        "dd.MMM.yyyy",
                        Locale.getDefault()
                    ).format(it)
                }
                registerContextMenu(itemView)
                itemView.setOnClickListener { view: View? ->
                    if (itemClickListener != null) {
                        itemClickListener!!.onItemClick(view, adapterPosition)
                    }
                }
                itemView.setOnLongClickListener {
                    itemView.showContextMenu()
                    menuPosition = layoutPosition
                    true
                }
            }
        }
    }

    private fun registerContextMenu(itemView: View) {
        fragment?.registerForContextMenu(itemView)
    }

}
