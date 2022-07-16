package kg.geektech.newsapp.ui.news

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.newsapp.databinding.ItemNewsBinding
import kg.geektech.newsapp.models.News
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val onClick : (position : Int) -> Unit,
    private val onLongClick : (news: News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val list = arrayListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.WHITE)
        }else{
            holder.itemView.setBackgroundColor(Color.GRAY)
        }
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addItem(news: News) {
        list.add(0, news)
        notifyItemInserted(0)
//+1        notifyItemInserted(list.indexOf(news))
    }

    fun addItems(list: List<News>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getList(){
        notifyDataSetChanged()
    }

    fun getItem(position : Int): News {
        return list[position]
    }

    fun replaceItem(news: News, poss: Int){
        list.set(poss, news)
        notifyItemChanged(poss)
    }

    fun removeItem(news: News){
        this.list.remove(news)
    }


    inner class NewsViewHolder(private var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.textTitle.text = news.title
            binding.textDate.text = getData(news.createdAt, "dd MMM yyyy")
            binding.textTime.text = getData(news.createdAt, "HH:mm")

            itemView.setOnLongClickListener {
                onLongClick(news)
                return@setOnLongClickListener true
            }
        }

        fun getData(milliSeconds : Long , dateFormat : String) : String {
            val formatter = SimpleDateFormat(dateFormat)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }
    }
}