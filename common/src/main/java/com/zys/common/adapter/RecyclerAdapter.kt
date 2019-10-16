package com.zys.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*

@Suppress("unused")
open class RecyclerAdapter(
    private val support: RecyclerSupport,
    config: AsyncDifferConfig<ItemCell> = AsyncDifferConfig.Builder(object :
        DiffUtil.ItemCallback<ItemCell>() {
        override fun areItemsTheSame(oldItem: ItemCell, newItem: ItemCell) =
            oldItem.layoutResId() == newItem.layoutResId() && oldItem.itemId() == newItem.itemId()


        override fun areContentsTheSame(oldItem: ItemCell, newItem: ItemCell) =
            oldItem.itemContent() == newItem.itemContent()
    }).build()
) : RecyclerView.Adapter<RecyclerVH>() {

    private var recyclerSubmit = RecyclerSubmit()
    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), config).apply {
        addListListener { _, _ ->
            when (recyclerSubmit.pageIndex) {
                0 -> support.onRefresh?.invoke()
                else -> support.onLoadComplete?.invoke(
                    recyclerSubmit.pageIndex,
                    recyclerSubmit.pageCount,
                    recyclerSubmit.total
                )
            }
        }
    }

    override fun getItemViewType(position: Int) = differ.currentList[position].layoutResId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVH {
        differ.currentList.forEach {
            if (viewType == it.layoutResId()) {
                return it.onCreateViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                    ), support
                )
            }
        }
        throw IllegalArgumentException("viewType not found")
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerVH, position: Int, payloads: MutableList<Any>) {
        holder.bind(differ.currentList[position], payloads)
    }

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        //empty
    }

    fun currentList(): MutableList<ItemCell> = differ.currentList

    fun submitLoading(cell: ItemCell? = null) {
        recyclerSubmit = RecyclerSubmit(0)
        val newList = mutableListOf<ItemCell>()
        cell?.let {
            newList.add(it)
        }
        differ.submitList(newList)
    }

    /**
     * submit error
     */
    fun submitError(pageIndex: Int = 1, cell: ItemCell = DEF_ERROR_CELL) {
        recyclerSubmit = RecyclerSubmit(pageIndex)
        if (pageIndex == 1) {
            differ.submitList(mutableListOf(cell))
        } else {
            support.onLoadComplete?.invoke(pageIndex, 0, 0)
        }
    }

    /**
     * submit list
     */
    fun submitList(
        list: List<ItemCell>,
        block: RecyclerSubmit,
        cell: ItemCell = DEF_EMPTY_CELL
    ) {
        recyclerSubmit = block
        if (list.isEmpty()) {
            if (block.pageIndex == 1) {
                differ.submitList(mutableListOf(cell))
            } else {
                support.onLoadComplete?.invoke(block.pageIndex, block.pageCount, block.total)
            }
        } else {
            if (block.pageIndex == 1) {
                differ.submitList(list)
            } else {
                val temp = mutableListOf<ItemCell>()
                temp.addAll(differ.currentList)
                temp.addAll(list)
                differ.submitList(temp)
            }
        }
    }
}