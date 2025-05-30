#pragma once
#include "main.h"

class CObject;

class CTurnLeftAndRight //: public CEntity
{
public:
	CTurnLeftAndRight();
	~CTurnLeftAndRight();

public:
	/*enum eTurnState
	{
		TURN_OFF,
		TURN_LEFT,
		TURN_RIGHT,
		TURN_ALL
	};*/
	//CVehicleGta* 	m_pVehicle;

	// поворотники
	//CObject*		m_pLeftFrontTurnLighter;
	CObject*		m_pRightFrontTurnLighter;
	//CObject*		m_pLeftRearTurnLighter;
	CObject*		m_pRightRearTurnLighter;
	//eTurnState 		m_iTurnState;
	bool 			m_bIsOnRightTurnLight;
	bool  			m_bIsOnAllurnLight;
	bool 			m_bIsOnLeftTurnLight;
	bool 			m_bIsOnAllTurnLight;
};
