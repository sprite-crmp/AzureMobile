// -- -- -- -- -- -- -- 
// by Weikton 
// -- -- -- -- -- -- --
#include "BRClient.h"
#include <stdint.h>

#include "util/CJavaWrapper.h"
// 2 = 1 (времено, пока не добавили сервера)

const char* g_szServerNames[MAX_SERVERS] = {
	"Azure Mobile | Azure",
	"BLACK RUSSIA | TEST"
                  // 2 = 1 !!! 5.39.108.48:1273
};

const CSetServer::CServerInstanceEncrypted g_sEncryptedAddresses[MAX_SERVERS] = {
	CSetServer::create("5.39.108.48", 1, 16, 1273, false),
	CSetServer::create("5.39.108.48", 1, 16, 1273, false)
				
};