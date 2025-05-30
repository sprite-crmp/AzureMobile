#include <jni.h>
#include "main.h"
#include "game/game.h"
#include "net/netgame.h"
#include "settings.h"
#include "keyboard.h"
#include "chatwindow.h"
extern CGame *pGame;
extern CNetGame* pNetGame;
extern CSettings* pSettings;
extern CKeyBoard *pKeyBoard;
extern CChatWindow* pChatWindow;
//pKeyBoard->Open

extern "C" {
JNIEXPORT void JNICALL Java_com_nvidia_devtech_NvEventQueueActivity_OpenKeyBoarClient(JNIEnv * env , jobject thiz ) {

    pChatWindow->OOpen();

}
JNIEXPORT void JNICALL Java_ru_azure_games_gui_chatedgar_ChatManager_sendChatMessages(JNIEnv* pEnv, jobject thiz, jbyteArray str) {
    jboolean isCopy = true;

    jbyte* pMsg = pEnv->GetByteArrayElements(str, &isCopy);
    jsize length = pEnv->GetArrayLength(str);

    std::string szStr((char*)pMsg, length);

    pChatWindow->ChatWindowInputHandler((char*)szStr.c_str());

    pEnv->ReleaseByteArrayElements(str, pMsg, JNI_ABORT);
    }
}
