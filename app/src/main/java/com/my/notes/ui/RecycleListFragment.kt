package com.my.notes.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.FirebaseFirestore
import com.my.notes.MainActivity
import com.my.notes.R
import com.my.notes.data.*
import com.my.notes.data.RecycleAdapter.OnItemClickListener
import com.my.notes.observer.EventListener
import com.my.notes.observer.EventManager
import com.my.notes.ui.NoteFragment.Companion.newInstance
import com.my.notes.utils.COLLECTION_PATH
import java.text.SimpleDateFormat
import java.util.*

class RecycleListFragment : Fragment() {
    private var adapter: RecycleAdapter? = null
    private var dataSource: DataSource? = null
    private var eventManager: EventManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = RecycleAdapter(this)
        dataSource =
            DataSourceImp().init(object : CardDataResponse {
                override fun initialized(cardsData: DataSource?) {
                    adapter!!.notifyDataSetChanged()
                }
            })
        return inflater.inflate(R.layout.recycle_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycle_list)
        initItemAnimator(recyclerView)
        initDecorator(recyclerView)
        initDate(view)
        adapter!!.setDataSource(dataSource)
        initRecyclerView(recyclerView)
        initViews(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as MainActivity
        eventManager = activity.eventManager
    }

    override fun onDetach() {
        eventManager = null
        super.onDetach()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter!!.menuPosition
        when (item.itemId) {
            R.id.context_change -> {
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragment_container,
                    newInstance(dataSource!!.getData(position))
                )
                transactionCommit(fragmentTransaction)
                eventManager!!.subscribe(object : EventListener {
                    override fun updateData(cardData: CardData?) {
                        dataSource!!.changeData(position, cardData!!)
                        adapter!!.notifyItemChanged(position)
                    }
                })
                return true
            }
            R.id.context_delete -> {
                val bundle = Bundle()
                bundle.putInt("position_to_delete", position)
                val noteDialogFragment = NoteDialogFragment()
                noteDialogFragment.arguments = bundle
                val firebaseFirestore = FirebaseFirestore.getInstance()
                firebaseFirestore.collection(COLLECTION_PATH)
                    .document(dataSource!!.getData(position).id!!).delete()
                    .addOnSuccessListener {
                        Log.d(
                            "Success TAG",
                            "Its ok with reading Firebase "
                        )
                    }
                    .addOnFailureListener { e: Exception ->
                        Log.d(
                            "Error TAG",
                            "get failed with reading Firebase -$e"
                        )
                    }
                dataSource!!.deleteData(position)
                adapter!!.notifyItemRemoved(position)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun initViews(view: View) {
        val addNoteButton: MaterialButton = view.findViewById(R.id.buttonAddNote)
        addNoteButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, newInstance())
            transactionCommit(fragmentTransaction)
            eventManager!!.subscribe(object : EventListener {
                override fun updateData(cardData: CardData?) {
                    dataSource!!.addData(cardData!!)
                    adapter!!.notifyItemInserted(dataSource!!.size() - 1)
                }
            })
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter!!.setItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragment_container,
                    newInstance(dataSource!!.getData(position))
                )
                transactionCommit(fragmentTransaction)
                eventManager!!.subscribe(object : EventListener {
                    override fun updateData(cardData: CardData?) {
                        dataSource!!.changeData(position, cardData!!)
                        adapter!!.notifyItemChanged(position)
                    }
                })
            }
        })
    }

    private fun initItemAnimator(recyclerView: RecyclerView) {
        val itemAnimator: ItemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        recyclerView.itemAnimator = itemAnimator
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initDecorator(recyclerView: RecyclerView) {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun transactionCommit(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.addToBackStack("")
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    private fun initDate(view: View) {
        val textViewDate: MaterialTextView = view.findViewById(R.id.textViewDateRL)
        val sdf = SimpleDateFormat("HH:mm, dd-EEEE-yyyy", Locale.getDefault())
        val date = sdf.format(Calendar.getInstance().time)
        textViewDate.text = date
    }
}