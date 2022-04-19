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