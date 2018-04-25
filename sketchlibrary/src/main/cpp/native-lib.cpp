#include <jni.h>
#include <string.h>
#include "imageprocess.cpp"

extern "C" {

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_applyRGBCurve(JNIEnv *env,
                                                                jobject obj,
                                                                jintArray pixels,
                                                                jintArray rgb,
                                                                jint width,
                                                                jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    jint *RGBBuff = getPointerArray(env, rgb);
    applyRGBCurve(width, height, pixelsBuff, RGBBuff);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    releaseArray(env, rgb, RGBBuff);
    return result;
}

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_applyChannelCurves(JNIEnv *env,
                                                                     jobject obj,
                                                                     jintArray pixels,
                                                                     jintArray r,
                                                                     jintArray g,
                                                                     jintArray b,
                                                                     jint width,
                                                                     jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    jint *RBuff = getPointerArray(env, r);
    jint *GBuff = getPointerArray(env, g);
    jint *BBuff = getPointerArray(env, b);
    applyChannelCurves(width, height, pixelsBuff, RBuff, GBuff, BBuff);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    releaseArray(env, r, RBuff);
    releaseArray(env, g, GBuff);
    releaseArray(env, b, BBuff);
    return result;
}

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_doBrightness(JNIEnv *env,
                                                                      jobject obj,
                                                                      jintArray pixels,
                                                                      jint value,
                                                                      jint width,
                                                                      jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    brightness(width, height, pixelsBuff, value);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    return result;
}

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_doContrast(JNIEnv *env,
                                                                    jobject obj,
                                                                    jintArray pixels,
                                                                    jfloat value,
                                                                    jint width,
                                                                    jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    contrast(width, height, pixelsBuff, value);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    return result;
}

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_doColorOverlay(JNIEnv *env,
                                                                        jobject obj,
                                                                        jintArray pixels,
                                                                        jint depth,
                                                                        jfloat red,
                                                                        jfloat green,
                                                                        jfloat blue,
                                                                        jint width,
                                                                        jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    colorOverlay(pixelsBuff, depth, red, green, blue, width, height);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    return result;
}

JNIEXPORT jintArray
Java_com_intretech_sketcher_library_sketch_NativeSketcher_doSaturation(JNIEnv *env,
                                                                       jobject obj,
                                                                       jintArray pixels,
                                                                       jfloat level,
                                                                       jint width,
                                                                       jint height) {
    jint *pixelsBuff = getPointerArray(env, pixels);
    saturation(pixelsBuff, level, width, height);
    jintArray result = convertIntoArray(env, width * height, pixelsBuff);
    releaseArray(env, pixels, pixelsBuff);
    return result;
}

}