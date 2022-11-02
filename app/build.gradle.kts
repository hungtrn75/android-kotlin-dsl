plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.skymapglobal.cctest"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Network
    implementation(Dependencies.retrofit)
    // Timber
    implementation(Dependencies.timber)
    // Coroutines
    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutineAndroid)
    // Koin
    implementation(Dependencies.koinCore)
    implementation(Dependencies.koinAndroid)
    // Serialization
    implementation(Dependencies.serializationJson)
    // FP
    implementation(Dependencies.arrow)
    //
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppCompat)
    implementation(Dependencies.googleMaterialDesign)
    implementation(Dependencies.androidxConstraintLayout)
    implementation(Dependencies.androidxNavigationFragmentKtx)
    implementation(Dependencies.androidxNavigationUiKtx)
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    implementation("androidx.webkit:webkit:1.4.0")
    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.androidxTestJUnit)
    androidTestImplementation(Dependencies.androidxTestEspresso)
}