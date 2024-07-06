package com.example.moneyhome.ui.history
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.example.moneyhome.databinding.HistoryItemBinding
import java.text.SimpleDateFormat
import java.util.Locale
class HistoryAdapter(
    private val onDeleteClickListener: (Int) -> Unit
) : ListAdapter<TransactionEntity, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    class HistoryViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale")
        fun bind(transaction: TransactionEntity, onDeleteClickListener: (Int) -> Unit) {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            binding.textViewDate.text = dateFormat.format(transaction.date)
            binding.textViewType.text = transaction.type
            binding.textViewCategory.text = transaction.category
            binding.textViewAmount.text = String.format("%.2f", transaction.amount)
            binding.textViewComment.text = transaction.comment
            binding.imageViewDelete.setOnClickListener {
                onDeleteClickListener(transaction.id)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TransactionEntity>() {
        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction, onDeleteClickListener)
    }
}