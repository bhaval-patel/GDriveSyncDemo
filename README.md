#GDriveSyncDemo
1. Create new project in android studio  (refer step1.png, step2.png)
2. add below dependencies in you build.gradle file which is in app module  (refer step3.png)
		
	implementation 'androidx.multidex:multidex:2.0.1'
    implementation "com.google.android.gms:play-services-auth:16.0.1"
    implementation 'com.google.http-client:google-http-client-gson:1.26.0'
    implementation('com.google.api-client:google-api-client-android:1.26.0')
    implementation('com.google.apis:google-api-services-drive:v3-rev136-1.25.0') {
	    exclude group: 'com.google.guava'
	}

	also add packagingOptions in android { }

	packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/ASL2.0'
    }

3. Create new project in google api console (https://console.cloud.google.com/apis/dashboard) (refer step4.png)
4. Enable Google Drive Api from APIs & Services -> Library -> Google Drive API (refer step5.png)
5. Add Application Name in OAuth consent screen (refer step6.png)
6. Add scope -> /auth/drive.file (refer step7.png)
7. Now Create Credentials from APIs & Services -> Credentials -> Create credentials -> Create OAuth client ID (refer step8.png, step9.png)
8. just copy Client ID & Client Secret and paste to string.xml file (refer step10.png)

For more information in detail, you can also refer this step-by-step guide for Google Drive Auto-sync demo (https://www.spaceotechnologies.com/auto-sync-google-drive-android-app/).
