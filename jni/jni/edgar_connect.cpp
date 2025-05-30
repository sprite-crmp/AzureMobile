#include <jni.h>
#include "main.h"
#include "game/game.h"
#include "net/netgame.h"
#include "settings.h"
extern CGame *pGame;
extern CNetGame* pNetGame;
extern CSettings* pSettings;

extern "C" {
	JNIEXPORT void JNICALL Java_com_nvidia_devtech_NvEventQueueActivity_EdgarConnect(JNIEnv *env, jobject thiz, jstring host, jint port)
{
	const char *host_char = env->GetStringUTFChars(host, 0);
	pNetGame = new CNetGame(host_char, port,pSettings->GetReadOnly().szNickName, pSettings->GetReadOnly().szPassword);

        env->ReleaseStringUTFChars(host, host_char);
	}
}
