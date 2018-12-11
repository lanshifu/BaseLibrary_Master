/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_lanshifu_demo_module_ndk_JniTest */

#ifndef _Included_com_lanshifu_demo_module_ndk_JniTest
#define _Included_com_lanshifu_demo_module_ndk_JniTest
#ifdef __cplusplus
extern "C" {
#endif

//c调用java方法
void callJavaMethod(JNIEnv *env, jobject jobject){
    //找到类

    char* classname = "com/lanshifu/demo_module/ndk/JniTest";
    jclass clazz = (*env)->FindClass(env,"com/lanshifu/demo_module/ndk/JniTest");
    if(clazz == NULL){
        printf("find class com/lanshifu/demo_module/ndk/JniTest");
        return;
    }
    //找到要调用的方法的methodID     GetMethodID  GetStaticMethodID
//    jmethodID id = (*env)->GetStaticMethodID(env,clazz,"methodCallByJni","(Ljava/lang/String;)v"); // void 有参数方法
    jmethodID id2 = (*env)->GetStaticMethodID(env,clazz,"methodCallByJni","()v"); //void 无参方法
//    jmethodID id3 = (*env)->GetStaticMethodID(env,clazz,"methodCallByJni","()v"); //void 无参方法
    if(id2 == NULL){
        printf("can not find methodCallByJni ");
        return;
    }
    jstring msg = (*env)->NewStringUTF(env,"c 调用java成功");
    //调用void方法，传方法id
    (*env)->CallStaticVoidMethod(env,clazz,id2,msg);

}

/*
 * Class:     com_lanshifu_demo_module_ndk_JniTest
 * Method:    get
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_lanshifu_demo_1module_ndk_JniTest_get
  (JNIEnv *env, jobject jobject){
//         callJavaMethod(env,jobject);
        //返回一个字符串
        return (*env)->NewStringUTF(env,"This is my first NDK Application");
  }

/*
 * Class:     com_lanshifu_demo_module_ndk_JniTest
 * Method:    set
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_lanshifu_demo_1module_ndk_JniTest_set
  (JNIEnv *, jobject, jstring);



#ifdef __cplusplus
}
#endif
#endif