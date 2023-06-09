It's and Android App with User and Admin Module
Admin Module :
Admin can perform CRUD operation on Users and can change route information (Firebase db and Storage)
User Module:
Eligible user can generate QR Code
Can Chat with Other Users (Implemented Using Firebase Realtime Database)
Can see current location and Destination (Google Maps Api)
Can see all routes information
![image](https://github.com/madilkhan002/Transport-Manager/assets/75298232/734e6b1c-2a34-44a3-b1c2-74b4f9bc6ae4)

dependencies {

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.gms:play-services-maps:18.1.0'
    implementation 'com.google.firebase:firebase-database-ktx:20.1.0'
    implementation 'com.google.firebase:firebase-database:20.0.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation platform('com.google.firebase:firebase-bom:31.4.0')

    implementation 'androidx.navigation:navigation-fragment:2.5.3'
    implementation 'androidx.navigation:navigation-ui:2.5.3'

    implementation 'com.google.firebase:firebase-database-ktx:20.1.0'
    implementation 'com.google.firebase:firebase-storage-ktx:20.1.0'
    implementation 'com.google.firebase:firebase-core:20.1.0'
    implementation 'com.google.firebase:firebase-analytics:20.1.0'



    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.github.bumptech.glide:glide:4.15.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.15.0'
    //qr code
    implementation 'com.google.zxing:core:3.4.1'
    // maps
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:18.0.0'
}
