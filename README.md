# FYP_Found
## Project name: Found it 
this is a final year project in IVE MAD HD.</br>
features of Found:</br>
- [x] Chat box </br>
- [x] membership</br>
- [x] upload post</br>
- [x] classifier image with ML kit of from firebase </br>
- [x] GPS</br>
- [x] immediately message notification (the application will show a notification when someone send a message to other one) </br>
- [x] Edit user profile </br>
- [x] user can login with her google account</br>
- [ ] classifier image with self-model </br>
- [ ] user can login with her facebook account </br>

## Capture
><img width="352" alt="ChooseMethodToLogin" src="https://user-images.githubusercontent.com/50822564/111061951-eab1e580-84e0-11eb-911e-b2489eb1bd48.png">
><img width="352" alt="LoginPage" src="https://user-images.githubusercontent.com/50822564/111061980-1a60ed80-84e1-11eb-8475-1b6b8a0e3c81.png">
><img width="352" alt="SignUpPage" src="https://user-images.githubusercontent.com/50822564/111062003-45e3d800-84e1-11eb-9468-61e51972fd98.png">
><img width="352" alt="HomePage" src="https://user-images.githubusercontent.com/50822564/111062014-56944e00-84e1-11eb-933c-8685c6283677.png">
><img width="352" alt="UploadImage_chooseMethod" src="https://user-images.githubusercontent.com/50822564/111062031-6c097800-84e1-11eb-860c-27f5c5de1b19.png">
><img width="352" alt="螢幕截圖 2020-05-06 下午2 55 42" src="https://user-images.githubusercontent.com/50822564/111062056-9ce9ad00-84e1-11eb-9b19-1854294d549b.png">
><img width="352" alt="UploadImage_chooseMethod" src="https://user-images.githubusercontent.com/50822564/111062090-c9052e00-84e1-11eb-9811-e5975d2756c9.png">
><img width="352" alt="UploadImage_afterChoose" src="https://user-images.githubusercontent.com/50822564/111062098-d1f5ff80-84e1-11eb-8136-95790d0e451a.png">
><img width="352" alt="UploadImage_GetGPS" src="https://user-images.githubusercontent.com/50822564/111062276-d53dbb00-84e2-11eb-8350-b43e07b46f5a.png">
</br>
>after select a picture to the application, the applciation will classifier the image and output one to five label to the type field. it also get user current address at the same time.
><img width="352" alt="UploadImage_Result" src="https://user-images.githubusercontent.com/50822564/111062287-ef779900-84e2-11eb-8d68-3aa5e48ad281.png">
</br>
><img width="352" alt="螢幕截圖 2020-05-06 下午3 00 31" src="https://user-images.githubusercontent.com/50822564/111062316-159d3900-84e3-11eb-940a-48c03ba598d1.png"></br>
>owner need to answer three question, if there is two answer match with catcher answer. Then, application will jump to chat box page</br>
>> two result of answer right or not
>> <img width="352" alt="螢幕截圖 2020-05-06 下午3 00 36" src="https://user-images.githubusercontent.com/50822564/111062432-ab38c880-84e3-11eb-9c8a-0358f4f8799e.png">
>><img width="352" alt="螢幕截圖 2020-05-06 下午3 00 45" src="https://user-images.githubusercontent.com/50822564/111062438-b12ea980-84e3-11eb-8369-6abeb368b084.png"></br>
><img width="352" alt="螢幕截圖 2020-05-06 下午3 01 08" src="https://user-images.githubusercontent.com/50822564/111062440-ba1f7b00-84e3-11eb-8d6b-7761389d1661.png">
><img width="352" alt="螢幕截圖 2020-05-06 下午3 02 29" src="https://user-images.githubusercontent.com/50822564/111062447-c1df1f80-84e3-11eb-999c-fab1b9e98cf3.png"></br>
><img width="352" alt="notica" src="https://user-images.githubusercontent.com/50822564/111062454-cefc0e80-84e3-11eb-8caa-c5229027111b.png">
></br> 
>User profile(blank account)
><img width="352" alt="螢幕截圖 2020-05-06 下午3 05 47" src="https://user-images.githubusercontent.com/50822564/111062480-ffdc4380-84e3-11eb-9af8-78d70aca9b63.png"></br>
>User profile in Edit mode</br>
><img width="352" alt="螢幕截圖 2020-05-06 下午3 04 36" src="https://user-images.githubusercontent.com/50822564/111062493-17b3c780-84e4-11eb-9c91-27a83f405d33.png"></br>
>Edit post
><img width="352" alt="螢幕截圖 2020-05-06 下午2 56 51" src="https://user-images.githubusercontent.com/50822564/111062512-40d45800-84e4-11eb-9509-43c092f24107.png"></br>
>Change the status of property (switch the status)
><img width="352" alt="螢幕截圖 2020-05-06 下午2 56 55" src="https://user-images.githubusercontent.com/50822564/111062524-4b8eed00-84e4-11eb-9843-3fd6fabdf7d9.png">
>

























FIRSEBASE, MLKIT have been used in this project
the guilde in application 
```bash 
//firebase core
    implementation 'com.google.firebase:firebase-core:17.2.0'
    //firebase function.. sectors
    implementation 'com.google.firebase:firebase-analytics:17.2.0'
    implementation 'com.google.firebase:firebase-messaging:20.0.0'
    implementation 'com.google.firebase:firebase-auth:18.0.0'


    // facebook sdk for login and analysis
    implementation 'com.facebook.android:facebook-android-sdk:[4,5)'
    implementation 'com.google.android.gms:play-services-auth:17.0.0'
    // firebase ml kit depend
    implementation 'com.google.firebase:firebase-ml-vision:24.0.0'
    implementation 'com.google.firebase:firebase-ml-vision-image-label-model:19.0.0'
    //volley
    implementation 'com.android.volley:volley:1.1.1'
    //chat box depend (chat bar and recycle view)
    implementation 'com.cenkgun:chatbar:1.0.2'
    implementation 'com.google.firebase:firebase-database:19.2.0'
    // recycle view  decency
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.firebase:firebase-storage:19.1.0'
    implementation 'com.squareup.picasso:picasso:2.5.2'
    // QR code gener for share item
    implementation 'com.google.zxing:core:3.4.0'
    implementation 'com.google.android.material:material:1.0.0'
```
















Review/improvement:</br>
the project no use any design pattern to design.</br>
variable name may be need to uniform.</br>
file name need to normailze.</br>
there is many burdensome variable and function.</br>


