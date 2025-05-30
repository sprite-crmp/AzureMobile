#include <jni.h>
#include "TurnLeftAndRight.h"
#include "main.h"
#include "game/game.h"
#include "game/object.h"
#include "net/netgame.h"
#include "settings.h"
extern CGame *pGame;
extern CNetGame* pNetGame;
extern CSettings* pSettings;

CTurnLeftAndRight::CTurnLeftAndRight()
{
	//m_pLeftFrontTurnLighter = nullptr;
	m_pRightFrontTurnLighter = nullptr;
	//m_pLeftRearTurnLighter = nullptr;
	m_pRightRearTurnLighter = nullptr;
}

CTurnLeftAndRight::~CTurnLeftAndRight()
{
	/*delete m_pLeftFrontTurnLighter;
	m_pLeftFrontTurnLighter = nullptr;

	delete m_pLeftRearTurnLighter;
	m_pLeftRearTurnLighter = nullptr;*/

	delete m_pRightFrontTurnLighter;
	m_pRightFrontTurnLighter = nullptr;

	delete m_pRightRearTurnLighter;
	m_pRightRearTurnLighter = nullptr;

}


extern "C"
JNIEXPORT void JNICALL
Java_ru_azure_games_gui_Speedometer_sendClick(JNIEnv *env, jobject thiz, jint click_id) {
    switch(click_id)
    {
        case 0:
        {
            pNetGame->SendChatCommand("/engine");
            break;
        }
        case 4:
        {
            pNetGame->SendChatCommand("/lights");
            break;
        }
        case 1:
        {
            /*m_pRightFrontTurnLighter = pGame->NewObject(19294, 0.0, 0.0, 0.0, 0.0, 100.0);
            m_pRightFrontTurnLighter->AttachToVehicle(CVehicle::m_pVehicle, -0.9, 2.5, 0.1, 0.0, 0.0);

        	m_pRightRearTurnLighter = pGame->NewObject(19294, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);
        	m_pRightRearTurnLighter->AttachToVehicle(CVehicle::m_pVehicle, -0.9, 2.5, 0.1, 0.0, 0.0, 0.0);*/
            /*CPlayerPed *pPlayerPed = pNetGame->GetPlayerPool()->GetLocalPlayer()->m_pPlayerPed;
            CVehicle* pVehicle = pPlayerPed->GetCurrentVehicle();

            if(pVehicle->m_iTurnState == CVehicle::eTurnState::TURN_LEFT)
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_OFF;
            else
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_LEFT;
                */

           break;
        }
        case 2:
        {
            /*CPlayerPed *pPlayerPed = pNetGame->GetPlayerPool()->GetLocalPlayer()->m_pPlayerPed;
            CVehicle* pVehicle = pPlayerPed->GetCurrentVehicle();

            if(pVehicle->m_iTurnState == CVehicle::eTurnState::TURN_RIGHT)
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_OFF;
            else
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_RIGHT;

            break;*/
        }
        case 3:
        {
           /* CPlayerPed *pPlayerPed = pNetGame->GetPlayerPool()->GetLocalPlayer()->m_pPlayerPed;
            CVehicle* pVehicle = pPlayerPed->GetCurrentVehicle();

            if(pVehicle->m_iTurnState == CVehicle::eTurnState::TURN_ALL)
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_OFF;
            else
                pVehicle->m_iTurnState = CVehicle::eTurnState::TURN_ALL;
*/
            break;
        }
    }
}