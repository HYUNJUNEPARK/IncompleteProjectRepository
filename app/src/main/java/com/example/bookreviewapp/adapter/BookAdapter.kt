package com.example.bookreviewapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookreviewapp.databinding.ItemBookBinding
import com.example.bookreviewapp.model.BookDetailDto

/*ListAdapter
DiffUtil 을 활용해서 리스트를 업데이트 할 수 있는 기능을 추가한 Adapter
List 데이터를 표현해주며 List 를 백그라운드 스레드에서 diff(차이)를 처리하는 특징이 있다.

-getCurrentList() : 현재 리스트를 반환한다.
-onCurrentListChanged() : 리스트가 업데이트 되었을 때 실행할 콜백을 지정할 수 있다.
-submitList(List) : 리스트 데이터를 교체할 때 사용한다.

어댑터 내에서 리스트를 정의하는게 아니라 리스트 자체에서 데이터 리스트를 정의하기 때문에
ListAdapter<데이터클래스, 리사이클러뷰 뷰홀더>를 인자로 받는다.

RecyclerViewAdapter 와 다르게 getItemCount() 구현안해도 됨
*/

class BookAdapter(private val itemClickedListener: (BookDetailDto) -> Unit) : ListAdapter<BookDetailDto, BookAdapter.BookItemViewHolder>(diffUtil) {
    //리사이클러뷰가 실제로 뷰 포지션이 변경되었을 때 새로운 값을 할당할지 말지 결정하는 기준
    //같은 아이템이 올라오면 다시 할당할 필요가 없다 이런걸 판단해 주는게 diffUtil
    companion object {
        /*DiffUtil
        기존 리스트와 업데이트 된 리스트의 차이를 계산하고 실제로 변환할 리스트 아이템들의 결과를 반환하는 유틸리티 클래스
         RecyclerView Adpater의 업데이트를 계산하는데 사용되고 ListAdapter에서 DiffUtil을 활용해서 차이점을 계산
        */
        val diffUtil = object : DiffUtil.ItemCallback<BookDetailDto>() {
            // 두 아이템이 동일한 아이템인지 체크. 보통 고유한 id를 기준으로 비교
            override fun areItemsTheSame(oldItem: BookDetailDto, newItem: BookDetailDto): Boolean {
                return oldItem.id == newItem.id
            }
            // 두 아이템이 동일한 내용을 가지고 있는지 체크. areItemsTheSame()이 true일때 호출됨
            override fun areContentsTheSame(oldItem: BookDetailDto, newItem: BookDetailDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    //아이템 레이아웃을 바인딩한 후 '뷰홀더' 에 넘겨줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(binding)
    }

    /*
    생성된 뷰홀더를 화면에 보여주는 클래스
    데이터 인덱스 파라미터를 갖고 있으며 holder 클래스에서 만든 함수에 이를 전달해줘 개별 데이터를 화면에 보여줄 수 있음
    ??? 리스트 어댑터의 데이터는 currentList 에 미리 저장되어 있음
    */
    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    /*개별 데이터에 대응
    바인딩 객체를 파라미터로 받아고 ui 객체를 가져와 개별 데이터를 바인딩
    목록에서 아이템 하나가 클릭외었을 때 홀더가 갖고 있는 아이템 바인딩에 클릭 리스너를 달아 처리
    ui 작업이 필요한 함수를 여기서 작성하고 onBindViewHolder 에서 호출해 사용할 수 있음*/
    inner class BookItemViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookModel: BookDetailDto) {
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description
            Glide.with(binding.coverImageView.context)
                 .load(bookModel.coverSmallUrl)
                 .into(binding.coverImageView)

            //class BookAdapter(private val itemClickedListener: (BookDetailDto) -> Unit)
            //binding.root -> item_book.xml : 아이템 객체를 클릭하면 동작함
            binding.root.setOnClickListener {
                itemClickedListener(bookModel)
            }
        }
    }
}
