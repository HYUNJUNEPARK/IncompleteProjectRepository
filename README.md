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

RecyclerView


Glide
-이미지로딩

Room library
-최근 기록 저장



포스트맨 - API 테스트
https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=ko

API 키 관리
https://otu165.tistory.com/21

인터파크 오픈 API
http://book.interpark.com/bookPark/html/bookpinion/api_main.html

Retrofit 사용법
https://square.github.io/retrofit/
https://joycehong0524.medium.com/android-studio-retrofit2-%EA%B8%B0%EB%B3%B8-%EC%82%AC%EC%9A%A9%EB%B2%95-retrofit-%EC%9D%98%EB%AC%B8%EC%A0%90-%ED%92%80%EC%96%B4%ED%97%A4%EC%B9%98%EA%B8%B0-%EC%8A%A4%EC%95%95-f150db436add

Retrofit 최신 버전 확인 -> github tags
https://github.com/square/retrofit