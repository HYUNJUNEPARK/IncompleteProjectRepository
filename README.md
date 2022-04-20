Firebase Store

Firebase Realtime Database

Firebase Authentication

//////

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

/////////////////
Glide
`implementation 'com.github.bumptech.glide:glide:4.12.0'`
`Glide.with(binding.thumbnailImageView).load(articleModel.imageUrl).into(binding.thumbnailImageView)`



앱 프로젝트 - 14 - 1
https://velog.io/@odesay97/%EC%95%B1-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-13-1-%EC%A4%91%EA%B3%A0%EA%B1%B0%EB%9E%98-%EC%95%B1-


ValueEventListener/ChildEventListenr
https://firebase.google.com/docs/database/android/lists-of-data?hl=ko