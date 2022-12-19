Retrofit 라이브러리
-HttpURLConnection 클래스보다 더 간단하게 HTTP 로 데이터를 통신하는 라이브러리
-Retrofit Interface 를 해석해 HTTP 통신 처리(레트로핏 인터페이스는 실행 방식데이터만 있고 실행은 레트로핏이함)
-GSON 라이브러리(Retrofit 라이브러리로 가져온 데이터를 코틀린 클래스로 변환해주는 라이브러리)와 함께 사용

```kotlin
//AndroidManifest
<uses-permission android:name="android.permission.INTERNET"/>

//build.gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

//Retrofit Interface - InterParkBookInfo
interface InterParkBookInfo {
    @GET("베이스 Url 을 제외한 뒷부분") //베이스 url(변하지 않은 url) 데이터는 레트로핏 객체 생성 시 설정
    fun getBooksByName(
        //요청 변수 설정 - 내 API key, 검색하고 싶은 키워드
        @Query("key") apiKey: String,
        @Query("query") keyword : String
    ) : Call<SearchBookDTO> //리턴 데이터를 SearchBookDTO 데이터 클래스로 받음
    //-> GET 형식으로 apiKey 와 keyword 를 서버로 보내서 응답 데이터를 SearchBookDTO 로 받음
}

//Retrofit 객체 생성
val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

//서버로 부터 데이터 가져오기
bookService.getBestSellerBooks(userAPIKey)
    .enqueue(object: Callback<BestSellerDTO> {
        override fun onResponse(call: Call<BestSellerDTO>, response: Response<BestSellerDTO>) {
            if(response.isSuccessful.not()) { return }
            //body 에 BestSellerDTO 형식의 데이터가 들어있음
            response.body()?.let {
                it.bookDetail.forEach { a_book_info ->
                    //...
                }
            }
        }
        override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
            //...
        }
    })
```

//////////////////////////////////////////////////////////////////////////////////////////


DTO(Data Transfer Object)
-특정 테이블의 정보를 레코드 단위로 정의해놓은 클래스
cf. DAO(Data Access Object) : DB에 전송 명령을 전담하는 메소드를 모아놓은 클래스
-DAO 가 서버에 '특정 데이터 100개를 받아오라' 고 명령 -> DAO 는 100개의 DTO 를 생성해 리스트로 반환

```kotlin
/*@SerializedName
서버에서는 val itemId 로 데이터를 주는데 나는 val id 로 받고 싶을 때 itemId 와 id 를 매핑해주는 어노테이션*/

data class BookDetails(
    @SerializedName("itemId") val id : Long
    @SerializedName("item") val bookDetails : List<BookDetails>
    /*item 은 여러개의 데이터가 모인 리스트 형식으로 데이터를 담고 있는데
    필요한 데이터를 레코드화 해 BookDetailDTO 로 받음
    */
)
```

/////////////////////////////////////////////////////////////////////////////////////////////

ListAdapter

```kotlin

class Adapter: RecyclerView.Adapter<Holder>() {

}

class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate((LayoutInflater.from(parent.context), parent, false))
        return Holder(binding)
     }
}

```
/////
DB 가 이미 있는데 새로운 dB 를 만든다면 충돌로 인해 앱이 크래쉬 될 수 있음 (version = 1)
Histroy로 db를 만든 후에 Review로 만들었을 때 발생함


@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDAO
    abstract fun reviewDao(): ReviewDao
}

->



//////////////
``` 텍스트 뷰가 길어저도 라인은 한개만 표시 할 때
    <TextView

        android:lines="1"
        android:ellipsize="end"
 />

         android:maxLines="3"
         android:textSize="12sp"
         android:ellipsize="end"


검색할 때 라인 제한과 인풋타입 제한
    <EditText
        android:id="@+id/searchEditText"
        android:layout_width="0dp"
        android:lines="1"
        android:inputType="text"

binding.searchEditText.setOnKeyListener
에디 텍스트에 키보드가 눌릴떄마다 이벤트를 받을 수 있음

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/bookRecyclerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        tools:listitem="@layout/item_book"
tools:listitem 를 통해 리사이클러뷰에 아이템리스트가 어떻게 추가될지 시뮬레이션 식으로 볼 수 있음



```

Glide
glide 로 이미지를 가져올 때 http 통신을 허용하지 않으면 이미지가 불려지지 않는 경우가 있음
-> AndroidManifest
```
    <application
        android:usesCleartextTraffic="true"

http 통신을 허용함
```

`implementation 'com.github.bumptech.glide:glide:4.13.0'`

/////////////////////////////////////////////////////////////////////////////


data class 직렬화
```
plugins {
    id 'kotlin-parcelize'
}
추가


데이터 클래스를 직렬화 가능하도록 설정
@Parcelize <<<요거랑
data class BookDetailDto(
    @SerializedName("itemId") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("coverSmallUrl") val coverSmallUrl : String
) : Parcelable <<<요거 추가


//직렬화 데이터 갖고 오기
val model = intent.getParcelableExtra<BookDetailDto>("bookModel")
```










/////////////////////////////////////////////////////////////////////////////////////////////

Glide
https://github.com/bumptech/glide

포스트맨 - API 테스트
https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=ko

API 키 관리
https://otu165.tistory.com/21

인터파크 오픈 API
http://book.interpark.com/bookPark/html/bookpinion/api_main.html
인터파크 도서 -> 북피니언 -> 관리 -> 오픈업 관리

리스트어댑터
https://velog.io/@24hyunji/AndroidKotlin-RecyclerView%EC%97%90%EC%84%9C-ListAdapter-DiffUtil-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0


Retrofit 사용법
https://square.github.io/retrofit/
https://joycehong0524.medium.com/android-studio-retrofit2-%EA%B8%B0%EB%B3%B8-%EC%82%AC%EC%9A%A9%EB%B2%95-retrofit-%EC%9D%98%EB%AC%B8%EC%A0%90-%ED%92%80%EC%96%B4%ED%97%A4%EC%B9%98%EA%B8%B0-%EC%8A%A4%EC%95%95-f150db436add

Retrofit 최신 버전 확인 -> github tags
https://github.com/square/retrofit

Room library
-최근 기록 저장