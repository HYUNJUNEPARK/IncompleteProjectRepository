Firebase Store

Firebase Realtime Database

Firebase Authentication

//////

프래그먼에서 액티비티 종료
`activity?.finish()`






구글 서비스 build
build.gradle(Module)
```
plugins {
    id 'com.google.gms.google-services'
}
//...
dependencies {
    implementation platform('com.google.firebase:firebase-bom:26.8.0')
}
```

build.gradle(Project)
```
dependencies {
    classpath "com.google.gms:google-services:4.3.5"
}
```

파이어베이스 라이브러리
```
implementation platform('com.google.firebase:firebase-bom:26.8.0')
implementation 'com.google.firebase:firebase-database-ktx'
implementation 'com.google.firebase:firebase-auth-ktx'
implementation 'com.google.firebase:firebase-storage-ktx'
```



////
Json 직렬화/역직렬화 - Fast apply
https://tourspace.tistory.com/357?category=797357

https://www.codegrepper.com/code-examples/java/pass+model+class+in+intent+android





/////////////////
Glide
`implementation 'com.github.bumptech.glide:glide:4.12.0'`
`Glide.with(binding.thumbnailImageView).load(articleModel.imageUrl).into(binding.thumbnailImageView)`

앱 프로젝트 - 14 - 1
https://velog.io/@odesay97/%EC%95%B1-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-13-1-%EC%A4%91%EA%B3%A0%EA%B1%B0%EB%9E%98-%EC%95%B1-

ValueEventListener/ChildEventListener
https://firebase.google.com/docs/database/android/lists-of-data?hl=ko

Android ProgressBar 기본으로 제공하는 스타일
https://diyall.tistory.com/854

프래그먼에서 액티비티 finish() 메소드 구현 - 프래그먼트를 종료 시킴
https://yoon-dailylife.tistory.com/21

클릭 이벤트를 뒤에 배치된 레이아웃에 전달되지 않게 하기
https://fimtrus.tistory.com/entry/Android-%ED%81%B4%EB%A6%AD-%EC%9D%B4%EB%B2%A4%ED%8A%B8%EB%A5%BC-%EB%92%A4%EC%97%90-%EB%B0%B0%EC%B9%98%EB%90%9C-%EB%A0%88%EC%9D%B4%EC%95%84%EC%9B%83%EC%97%90-%EC%A0%84%EB%8B%AC%EB%90%98%EC%A7%80-%EC%95%8A%EA%B2%8C-%ED%95%98%EA%B8%B0

(둥근/사각) 테두리 있는 ImageView 간단히 만들기
https://ddangeun.tistory.com/129

Glide 라이브러리 테두리 속성
https://pluu.github.io/blog/android/2020/04/28/glide-fail-case/

Glide 라이브러리 속성
https://leveloper.tistory.com/162

파이어베이스 스토어 프로필 이미지
https://daldalhanstory.tistory.com/201

How to get a context in a recycler view adapter
https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter



java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
글라이드 컨텍스트 팁
https://eso0609.tistory.com/72


Fragment에서 Back Press 처리하기(with. OnBackPressedDispatcher)
https://readystory.tistory.com/186

Glide Library 사용시 참고 사항
-You cannot start a load for a destroyed activity
https://gogorchg.tistory.com/entry/Android-Glide-Library-%EC%82%AC%EC%9A%A9%EC%8B%9C-%EC%B0%B8%EA%B3%A0-%EC%82%AC%ED%95%AD

