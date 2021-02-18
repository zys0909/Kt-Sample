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

    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), config)

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

    val currentList: MutableList<ItemCell>
        get() =  differ.currentList


    /**
     * submit list
     */
    fun submit(list: List<ItemCell>, block: ()->Unit = {}) {
        val temp = mutableListOf<ItemCell>()
        temp.addAll(differ.currentList)
        temp.addAll(list)
        differ.submitList(temp){
            block.invoke()
        }
    }
}