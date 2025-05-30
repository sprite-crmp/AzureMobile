package ru.azure.games;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nvidia.devtech.NvEventQueueActivity;

import ru.azure.games.gui.ChooseServer;
import ru.azure.games.gui.HudManager;
import ru.azure.games.gui.Speedometer;
import ru.azure.games.gui.chatedgar.ChatManager;
import ru.azure.games.gui.dialogs.Dialog;
import ru.azure.games.gui.keyboard.KeyBoard;


public class InterfacesManager {
    private static InterfacesManager mInterfacesManager = null;
    public NvEventQueueActivity nvEventQueueActivity = null;
    public ViewGroup[] viewGroup;

    /*
    1 - Hud
    2 - Spedometr
    3 - Chat
    4 - Dialog
    5 - KeyBoard
    6 - LoaginMenu
    */

    private HudManager mHudManager = null;
    private Speedometer mSpeedometer = null;
    private ChatManager mChatManager = null;
    private Dialog mDialog = null;
    private KeyBoard mKeyBoard = null;
    private ChooseServer mChooseServer = null;

    public InterfacesManager(NvEventQueueActivity nvEventQueueActivity) {
        this.nvEventQueueActivity = nvEventQueueActivity;
        mInterfacesManager = this;
        viewGroup = new ViewGroup[256];

        mHudManager = new HudManager(nvEventQueueActivity);
        mSpeedometer = new Speedometer(nvEventQueueActivity);
        mChatManager = new ChatManager(nvEventQueueActivity, 3);
        mDialog = new Dialog(nvEventQueueActivity, 4);
        mKeyBoard = new KeyBoard(nvEventQueueActivity, 5);
        mChooseServer = new ChooseServer(nvEventQueueActivity);
    }

    public static InterfacesManager getInterfacesManager() {
        return mInterfacesManager;
    }

    public void AnimVisibale(ViewGroup viewGroup, int view) {
        if (viewGroup != null) {
            if (view == View.VISIBLE) {
                viewGroup.animate().setDuration(150).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        viewGroup.setVisibility(View.VISIBLE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(1.0f);
            }else {
                viewGroup.animate().setDuration(150).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        viewGroup.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0.0f);
            }
        }
    }

    public void hideViewGroup(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.setVisibility(View.GONE);
            viewGroup.setAlpha(0.0f);
        }
    }

    public void showViewGroup(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.setVisibility(View.VISIBLE);
            viewGroup.setAlpha(1.0f);
        }
    }

    public HudManager getHudManager() {
        return mHudManager;
    }

    public Speedometer getSpeedometerManager() {
        return mSpeedometer;
    }

    public ChatManager getChatManager() {
        return mChatManager;
    }

    public Dialog getDialogManager() {
        return mDialog;
    }

    public KeyBoard getKeyBoardManager() {
        return mKeyBoard;
    }

    public ChooseServer getChooseServerManager() {
        return mChooseServer;
    }

}
