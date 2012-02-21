/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class at_wisch_joystick_JoystickManager */

#ifndef _Included_at_wisch_joystick_JoystickManager
#define _Included_at_wisch_joystick_JoystickManager
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    sdlInitNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_sdlInitNative
  (JNIEnv *, jclass);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    getNumOfJoysticks
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_getNumOfJoysticks
  (JNIEnv *, jclass);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    openJoystick
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_openJoystick
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    openFFJoystick
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_openFFJoystick
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    sdlInitEventsNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_sdlInitEventsNative
  (JNIEnv *, jclass);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    enableJoystickEventPollingNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_enableJoystickEventPollingNative
  (JNIEnv *, jclass);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    disableJoystickEventPollingNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_JoystickManager_disableJoystickEventPollingNative
  (JNIEnv *, jclass);

/*
 * Class:     at_wisch_joystick_JoystickManager
 * Method:    closeSDLnative
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_at_wisch_joystick_JoystickManager_closeSDLnative
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class at_wisch_joystick_EventPollThread */

#ifndef _Included_at_wisch_joystick_EventPollThread
#define _Included_at_wisch_joystick_EventPollThread
#ifdef __cplusplus
extern "C" {
#endif
#undef at_wisch_joystick_EventPollThread_MIN_PRIORITY
#define at_wisch_joystick_EventPollThread_MIN_PRIORITY 1L
#undef at_wisch_joystick_EventPollThread_NORM_PRIORITY
#define at_wisch_joystick_EventPollThread_NORM_PRIORITY 5L
#undef at_wisch_joystick_EventPollThread_MAX_PRIORITY
#define at_wisch_joystick_EventPollThread_MAX_PRIORITY 10L
/*
 * Class:     at_wisch_joystick_EventPollThread
 * Method:    pollForEvent
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_at_wisch_joystick_EventPollThread_pollForEvent
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class at_wisch_joystick_Joystick */

#ifndef _Included_at_wisch_joystick_Joystick
#define _Included_at_wisch_joystick_Joystick
#ifdef __cplusplus
extern "C" {
#endif
#undef at_wisch_joystick_Joystick_POV_CENTERED
#define at_wisch_joystick_Joystick_POV_CENTERED -1L
#undef at_wisch_joystick_Joystick_POV_UP
#define at_wisch_joystick_Joystick_POV_UP 0L
#undef at_wisch_joystick_Joystick_POV_UP_RIGHT
#define at_wisch_joystick_Joystick_POV_UP_RIGHT 4500L
#undef at_wisch_joystick_Joystick_POV_RIGHT
#define at_wisch_joystick_Joystick_POV_RIGHT 9000L
#undef at_wisch_joystick_Joystick_POV_DOWN_RIGHT
#define at_wisch_joystick_Joystick_POV_DOWN_RIGHT 13500L
#undef at_wisch_joystick_Joystick_POV_DOWN
#define at_wisch_joystick_Joystick_POV_DOWN 18000L
#undef at_wisch_joystick_Joystick_POV_DOWN_LEFT
#define at_wisch_joystick_Joystick_POV_DOWN_LEFT 22500L
#undef at_wisch_joystick_Joystick_POV_LEFT
#define at_wisch_joystick_Joystick_POV_LEFT 27000L
#undef at_wisch_joystick_Joystick_POV_UP_LEFT
#define at_wisch_joystick_Joystick_POV_UP_LEFT 31500L
#undef at_wisch_joystick_Joystick_POV_AXIS_NEUTRAL
#define at_wisch_joystick_Joystick_POV_AXIS_NEUTRAL 0L
#undef at_wisch_joystick_Joystick_POV_AXIS_POSITIVE
#define at_wisch_joystick_Joystick_POV_AXIS_POSITIVE 1L
#undef at_wisch_joystick_Joystick_POV_AXIS_NEGATIVE
#define at_wisch_joystick_Joystick_POV_AXIS_NEGATIVE -1L
#undef at_wisch_joystick_Joystick_AXIS_NEUTRAL
#define at_wisch_joystick_Joystick_AXIS_NEUTRAL 0.0f
#undef at_wisch_joystick_Joystick_AXIS_MAXIMUM
#define at_wisch_joystick_Joystick_AXIS_MAXIMUM 1.0f
#undef at_wisch_joystick_Joystick_AXIS_MINIMUM
#define at_wisch_joystick_Joystick_AXIS_MINIMUM -1.0f
#undef at_wisch_joystick_Joystick_AXIS_DEFAULT_DEAD_ZONE
#define at_wisch_joystick_Joystick_AXIS_DEFAULT_DEAD_ZONE 0.03f
#undef at_wisch_joystick_Joystick_NATIVE_AXIS_MAX_VALUE_POSITIVE
#define at_wisch_joystick_Joystick_NATIVE_AXIS_MAX_VALUE_POSITIVE 32767L
#undef at_wisch_joystick_Joystick_NATIVE_AXIS_MAX_VALUE_NEGATIVE
#define at_wisch_joystick_Joystick_NATIVE_AXIS_MAX_VALUE_NEGATIVE 32768L
#undef at_wisch_joystick_Joystick_INFINITE_TIMES
#define at_wisch_joystick_Joystick_INFINITE_TIMES 2147483647L
#undef at_wisch_joystick_Joystick_DEFAULT_GAIN_VALUE
#define at_wisch_joystick_Joystick_DEFAULT_GAIN_VALUE 100L
#undef at_wisch_joystick_Joystick_DEFAULT_AUTOCENTER_VALUE
#define at_wisch_joystick_Joystick_DEFAULT_AUTOCENTER_VALUE 0L
/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getJoystickName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_at_wisch_joystick_Joystick_getJoystickName
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getNoOfButtons
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getNoOfButtons
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getNoOfPovs
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getNoOfPovs
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getNoOfAxes
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getNoOfAxes
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getNoOfBalls
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getNoOfBalls
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    poll
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_at_wisch_joystick_Joystick_poll
  (JNIEnv *, jobject);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    isButtonPressedNative
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_at_wisch_joystick_Joystick_isButtonPressedNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getPovValueXNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getPovValueXNative
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getPovValueYNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getPovValueYNative
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getPovDirectionNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getPovDirectionNative
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getAxisValueNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_Joystick_getAxisValueNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_Joystick
 * Method:    getBallDeltaNative
 * Signature: (II)[I
 */
JNIEXPORT jintArray JNICALL Java_at_wisch_joystick_Joystick_getBallDeltaNative
  (JNIEnv *, jclass, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class at_wisch_joystick_FFJoystick */

#ifndef _Included_at_wisch_joystick_FFJoystick
#define _Included_at_wisch_joystick_FFJoystick
#ifdef __cplusplus
extern "C" {
#endif
#undef at_wisch_joystick_FFJoystick_POV_CENTERED
#define at_wisch_joystick_FFJoystick_POV_CENTERED -1L
#undef at_wisch_joystick_FFJoystick_POV_UP
#define at_wisch_joystick_FFJoystick_POV_UP 0L
#undef at_wisch_joystick_FFJoystick_POV_UP_RIGHT
#define at_wisch_joystick_FFJoystick_POV_UP_RIGHT 4500L
#undef at_wisch_joystick_FFJoystick_POV_RIGHT
#define at_wisch_joystick_FFJoystick_POV_RIGHT 9000L
#undef at_wisch_joystick_FFJoystick_POV_DOWN_RIGHT
#define at_wisch_joystick_FFJoystick_POV_DOWN_RIGHT 13500L
#undef at_wisch_joystick_FFJoystick_POV_DOWN
#define at_wisch_joystick_FFJoystick_POV_DOWN 18000L
#undef at_wisch_joystick_FFJoystick_POV_DOWN_LEFT
#define at_wisch_joystick_FFJoystick_POV_DOWN_LEFT 22500L
#undef at_wisch_joystick_FFJoystick_POV_LEFT
#define at_wisch_joystick_FFJoystick_POV_LEFT 27000L
#undef at_wisch_joystick_FFJoystick_POV_UP_LEFT
#define at_wisch_joystick_FFJoystick_POV_UP_LEFT 31500L
#undef at_wisch_joystick_FFJoystick_POV_AXIS_NEUTRAL
#define at_wisch_joystick_FFJoystick_POV_AXIS_NEUTRAL 0L
#undef at_wisch_joystick_FFJoystick_POV_AXIS_POSITIVE
#define at_wisch_joystick_FFJoystick_POV_AXIS_POSITIVE 1L
#undef at_wisch_joystick_FFJoystick_POV_AXIS_NEGATIVE
#define at_wisch_joystick_FFJoystick_POV_AXIS_NEGATIVE -1L
#undef at_wisch_joystick_FFJoystick_AXIS_NEUTRAL
#define at_wisch_joystick_FFJoystick_AXIS_NEUTRAL 0.0f
#undef at_wisch_joystick_FFJoystick_AXIS_MAXIMUM
#define at_wisch_joystick_FFJoystick_AXIS_MAXIMUM 1.0f
#undef at_wisch_joystick_FFJoystick_AXIS_MINIMUM
#define at_wisch_joystick_FFJoystick_AXIS_MINIMUM -1.0f
#undef at_wisch_joystick_FFJoystick_AXIS_DEFAULT_DEAD_ZONE
#define at_wisch_joystick_FFJoystick_AXIS_DEFAULT_DEAD_ZONE 0.03f
#undef at_wisch_joystick_FFJoystick_NATIVE_AXIS_MAX_VALUE_POSITIVE
#define at_wisch_joystick_FFJoystick_NATIVE_AXIS_MAX_VALUE_POSITIVE 32767L
#undef at_wisch_joystick_FFJoystick_NATIVE_AXIS_MAX_VALUE_NEGATIVE
#define at_wisch_joystick_FFJoystick_NATIVE_AXIS_MAX_VALUE_NEGATIVE 32768L
#undef at_wisch_joystick_FFJoystick_INFINITE_TIMES
#define at_wisch_joystick_FFJoystick_INFINITE_TIMES 2147483647L
#undef at_wisch_joystick_FFJoystick_DEFAULT_GAIN_VALUE
#define at_wisch_joystick_FFJoystick_DEFAULT_GAIN_VALUE 100L
#undef at_wisch_joystick_FFJoystick_DEFAULT_AUTOCENTER_VALUE
#define at_wisch_joystick_FFJoystick_DEFAULT_AUTOCENTER_VALUE 0L
/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    getFFCapabilitiesNative
 * Signature: (I)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_at_wisch_joystick_FFJoystick_getFFCapabilitiesNative
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    getNumOfAxesNative
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_getNumOfAxesNative
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    getPlayNumOfEffectsNative
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_getPlayNumOfEffectsNative
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    getStoreNumOfEffectsNative
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_getStoreNumOfEffectsNative
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    setGainNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_setGainNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    setAutocenterNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_setAutocenterNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    pause
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_pause
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    unpause
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_unpause
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    newEffectNative
 * Signature: (ILat/wisch/joystick/ffeffect/Effect;I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_newEffectNative
  (JNIEnv *, jclass, jint, jobject, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    playEffectNative
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_playEffectNative
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    getEffectStatusNative
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_at_wisch_joystick_FFJoystick_getEffectStatusNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    updateEffectNative
 * Signature: (IILat/wisch/joystick/ffeffect/Effect;I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_updateEffectNative
  (JNIEnv *, jclass, jint, jint, jobject, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    stopEffectNative
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_stopEffectNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    destroyEffectNative
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_at_wisch_joystick_FFJoystick_destroyEffectNative
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    stopAllNative
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_at_wisch_joystick_FFJoystick_stopAllNative
  (JNIEnv *, jclass, jint);

/*
 * Class:     at_wisch_joystick_FFJoystick
 * Method:    destroyAllNative
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_at_wisch_joystick_FFJoystick_destroyAllNative
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class at_wisch_joystick_ffeffect_Effect */

#ifndef _Included_at_wisch_joystick_ffeffect_Effect
#define _Included_at_wisch_joystick_ffeffect_Effect
#ifdef __cplusplus
extern "C" {
#endif
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_CONSTANT
#define at_wisch_joystick_ffeffect_Effect_EFFECT_CONSTANT 5100L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_SINE
#define at_wisch_joystick_ffeffect_Effect_EFFECT_SINE 5101L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_SQUARE
#define at_wisch_joystick_ffeffect_Effect_EFFECT_SQUARE 5102L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_TRIANGLE
#define at_wisch_joystick_ffeffect_Effect_EFFECT_TRIANGLE 5103L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_SAWTOOTHUP
#define at_wisch_joystick_ffeffect_Effect_EFFECT_SAWTOOTHUP 5104L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_SAWTOOHDOWN
#define at_wisch_joystick_ffeffect_Effect_EFFECT_SAWTOOHDOWN 5105L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_RAMP
#define at_wisch_joystick_ffeffect_Effect_EFFECT_RAMP 5106L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_SPRING
#define at_wisch_joystick_ffeffect_Effect_EFFECT_SPRING 5107L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_DAMPER
#define at_wisch_joystick_ffeffect_Effect_EFFECT_DAMPER 5108L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_INERTIA
#define at_wisch_joystick_ffeffect_Effect_EFFECT_INERTIA 5109L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_FRICTION
#define at_wisch_joystick_ffeffect_Effect_EFFECT_FRICTION 5110L
#undef at_wisch_joystick_ffeffect_Effect_EFFECT_CUSTOM
#define at_wisch_joystick_ffeffect_Effect_EFFECT_CUSTOM 5111L
#undef at_wisch_joystick_ffeffect_Effect_NO_BUTTON
#define at_wisch_joystick_ffeffect_Effect_NO_BUTTON 2147483647L
#undef at_wisch_joystick_ffeffect_Effect_INFINITE_LENGTH
#define at_wisch_joystick_ffeffect_Effect_INFINITE_LENGTH 2147483647L
#undef at_wisch_joystick_ffeffect_Effect_MAX_LEVEL
#define at_wisch_joystick_ffeffect_Effect_MAX_LEVEL 32767L
#undef at_wisch_joystick_ffeffect_Effect_MIN_LEVEL
#define at_wisch_joystick_ffeffect_Effect_MIN_LEVEL -32768L
#undef at_wisch_joystick_ffeffect_Effect_MAX_BUTTONS
#define at_wisch_joystick_ffeffect_Effect_MAX_BUTTONS 32767L
#undef at_wisch_joystick_ffeffect_Effect_MAX_DELAY
#define at_wisch_joystick_ffeffect_Effect_MAX_DELAY 60000L
#undef at_wisch_joystick_ffeffect_Effect_MAX_LENGTH
#define at_wisch_joystick_ffeffect_Effect_MAX_LENGTH 360000000L
#ifdef __cplusplus
}
#endif
#endif
/* Header for class at_wisch_joystick_ffeffect_direction_Direction */

#ifndef _Included_at_wisch_joystick_ffeffect_direction_Direction
#define _Included_at_wisch_joystick_ffeffect_direction_Direction
#ifdef __cplusplus
extern "C" {
#endif
#undef at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_POLAR
#define at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_POLAR 5067L
#undef at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_SPHERICAL
#define at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_SPHERICAL 5068L
#undef at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_CARTESIAN
#define at_wisch_joystick_ffeffect_direction_Direction_DIRECTION_CARTESIAN 5069L
#ifdef __cplusplus
}
#endif
#endif