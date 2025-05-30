#pragma once

#include <jni.h>

#include <string>

#define EXCEPTION_CHECK(env) \
	if ((env)->ExceptionCheck()) \ 
	{ \
		(env)->ExceptionDescribe(); \
		(env)->ExceptionClear(); \
		return; \
	}

class CJavaWrapper
{
	jobject activity;

	jmethodID s_GetClipboardText;
	jmethodID s_CallLauncherActivity;

                  jmethodID s_RadarBR;

	jmethodID s_ShowInputLayout;
	jmethodID s_HideInputLayout;

	jmethodID s_ShowClientSettings;
	jmethodID s_SetUseFullScreen;
	jmethodID s_MakeDialog;

	jmethodID s_showHud;
                  jmethodID s_hideHud;
	jmethodID s_updateHudInfo;

	jmethodID s_showradar;
        jmethodID s_hideradar;
	jmethodID s_hideCameditgui;

	jmethodID s_showGps;
                  jmethodID s_hideGps;

	jmethodID s_showZona;
	jmethodID s_hideZona;

                  jmethodID s_showx2;
	jmethodID s_hidex2;

	jmethodID s_updateSpeedInfo;

	jmethodID s_showNotification;
	jmethodID s_showMenu;

	jmethodID s_dateTime;
	jmethodID s_openTime;

	jmethodID s_showSpeed;
	jmethodID s_showSpeedCall;
	jmethodID s_hideSpeedCall;
	jmethodID s_hideSpeed;

	jmethodID s_checkVersion;
	jmethodID s_closeDialog;
	jmethodID s_showQuestDialog;
	jmethodID s_showLoading;
	jmethodID s_updateFuel;
	jmethodID s_showRudasklad;
	jmethodID s_showRuda;
	jmethodID s_showWantedhud;
	jmethodID s_showHudstata;
	jmethodID s_showDonate;
	jmethodID s_showPhone;
	jmethodID s_showPromouter;
	jmethodID s_showAdobnova;
	jmethodID s_showSberreg;
    jmethodID s_AddChatMessage;

	jmethodID s_setPauseState;

	jmethodID s_showSplash;
	jmethodID s_updateSplash;
public:
	JNIEnv* GetEnv();

	std::string GetClipboardString();
	void CallLauncherActivity(int type);

	void ShowInputLayout();
	void HideInputLayout();

	void ShowRadar();
	void HideRadar();
	void HideCameditgui(int clickhide);

	void SetRadar();

	void ShowClientSettings();

	void SetUseFullScreen(int b);

	void UpdateHudInfo(int health, int armour, int hunger, int weaponidweik, int ammo, int ammoinclip, int money, int wanted);
	void ShowHud();
                  void HideHud();

	void ShowGPS();
                  void HideGPS();

	void ShowZona();
                  void HideZona();

	void ShowX2();
                  void HideX2();

	void UpdateSpeedInfo(int speed, int fuel, int hp, int mileage, int engine, int light, int belt, int lock);\
	void ShowSpeed();
	void ShowSpeedCall();
	void HideSpeedCall();
    void HideSpeed();

	void MakeDialog(int dialogId, int dialogTypeId, char* caption, char* content, char* leftBtnText, char* rightBtnText); // Диалоги
	void ShowNotification(int type, char* text, int duration, char* actionforBtn, char* textBtn);
	void ShowMenu();

	void CheckVersion(int reytiz);

	void DateTimeUpd(char* datetime, int action);
	void OpenTime(int action);

	void CloseDialog();
	void ShowQuestDialog(char* player, char* playerdialog, int size, int time);
	void ShowLoading(int loadingbar);
	void UpdateFuel(int fuelbar);
	void ShowRudasklad(int rudaskladvar);
	void ShowRuda(int rudavar);
	void ShowWantedhud(int wantedhudd);
	void ShowHudstata(int lvlhudd);
	void ShowDonate(int reytiz);
	void ShowPhone(int reytiz);
	void ShowPromouter(int reytiz);
	void ShowAdobnova(char* player);
	void ShowSberreg(int reytiz);
    void AddChatMessage(const char msg[], int color);



    void SetPauseState(bool a1);

	void ShowSplash();
	void UpdateSplash(int progress, int i);

	CJavaWrapper(JNIEnv* env, jobject activity);
	~CJavaWrapper();
};

extern CJavaWrapper* g_pJavaWrapper;