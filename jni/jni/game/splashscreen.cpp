#include "../main.h"
#include "RW/RenderWare.h"
#include "game.h"
#include "../gui/gui.h"
#include "../util/CJavaWrapper.h"

extern CGUI* pGUI;

RwTexture* splashTexture = nullptr;

#define COLOR_WHITE		0xFFFFFFFF
#define COLOR_BLACK 	0xFF000000
#define COLOR_ORANGE 	0xFFFAA500
#define COLOR_PURPLE	0xFFCE39DF
#define COLOR_ROSE		0xFFFF99FF
#define COLOR_GREY		0xFF5d5d5d
#define COLOR_BRED		0xFF9933FF
#define COLOR_BLUE		0xFF6C2713
#define COLOR_CYAN		0xFFCE6816
#define COLOR_1			0xFFB58891
#define COLOR_2			0xFF673F40
#define COLOR_PIZDEC	0xFFff008b

struct stRect
{
	int x1;	// left
	int y1;	// top
	int x2;	// right
	int y2;	// bottom
};

struct stfRect
{
	float x1;
	float y1;
	float x2;
	float y2;
};

#define MAX_SCHEMAS 4
uint32_t colors[MAX_SCHEMAS][2] = {
	{ COLOR_BLACK,	COLOR_WHITE },
	{ COLOR_BLACK, 	COLOR_ORANGE },
	{ COLOR_BLACK,	COLOR_PIZDEC },
	{ COLOR_BLACK,	COLOR_BLUE }
};
unsigned int color_scheme = 0;

void LoadSplashTexture()
{
	//Log("Loading splash texture..");
	//splashTexture = (RwTexture*)LoadTextureFromDB("samp", "br-screen");

	//color_scheme = 1;
}

void Draw(stRect* rect, uint32_t color, RwRaster* raster = nullptr, stfRect* uv = nullptr)
{
	
}


void RenderSplash()
{
	const float percent = *(float*)(g_libGTASA + 0x8F08C0);
	if (percent <= 0.0f) return;
	float mult = percent / 100.0f;

	int intMult = (int)percent;

	if (intMult > 51)
                  { 
                          intMult = 99;
                  }

	g_pJavaWrapper->UpdateSplash(intMult, 0);

	/*rect.x1 = RsGlobal->maximumWidth * 0.05f;
	rect.y1 = RsGlobal->maximumHeight * 0.95f;
	rect.x2 = (RsGlobal->maximumWidth * 0.95f) * mult;
	rect.y2 = RsGlobal->maximumHeight * 0.97f;

	Draw(&rect, colors[color_scheme][1]);*/
}

void ImGui_ImplRenderWare_RenderDrawData(ImDrawData* draw_data);
void ImGui_ImplRenderWare_NewFrame();

void RenderSplashScreen()
{
	RenderSplash();
	g_pJavaWrapper->ShowSplash();

	if (!pGUI) return;

	ImGui_ImplRenderWare_NewFrame();
	ImGui::NewFrame();

	ImGui::EndFrame();
	ImGui::Render();
	ImGui_ImplRenderWare_RenderDrawData(ImGui::GetDrawData());
}