package ru.azure.games.gui.dialogs;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.nvidia.devtech.CustomEditText;
import com.nvidia.devtech.NvEventQueueActivity;

import ru.azure.games.InterfacesManager;
import ru.azure.games.R;
import ru.azure.games.gui.ChooseServer;
import ru.azure.games.gui.util.CustomRecyclerView;
import ru.azure.games.gui.util.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Dialog {
    private static final int DIALOG_LEFT_BTN_ID = 1;
    private static final int DIALOG_RIGHT_BTN_ID = 0;
    private static final int DIALOG_STYLE_INPUT = 1;
    private static final int DIALOG_STYLE_LIST = 2;
    private static final int DIALOG_STYLE_MSGBOX = 0;
    private static final int DIALOG_STYLE_PASSWORD = 3;
    private static final int DIALOG_STYLE_TABLIST = 4;
    private static final int DIALOG_STYLE_TABLIST_HEADER = 5;
    public NvEventQueueActivity nvEventQueueActivity;
    public ViewGroup viewGroup;
    private TextView mCaption;
    private TextView mContent;
    private int mCurrentDialogId = -1;
    private int mCurrentDialogTypeId = -1;
    private String mCurrentInputText = "";
    private int mCurrentListItem = -1;
    private CustomRecyclerView mCustomRecyclerView;
    private ArrayList<TextView> mHeadersList;
    private CustomEditText mInput;
    private FrameLayout mInputLayout;
    private FrameLayout mLeftBtn;
    private LinearLayout mListLayout;
    private ConstraintLayout mMsgBoxLayout;
    private FrameLayout mRightBtn;
    private ArrayList<String> mRowsList;

    public Dialog(NvEventQueueActivity nvEventQueueActivity, int guiId) {
        this.nvEventQueueActivity = nvEventQueueActivity;
        viewGroup = InterfacesManager.getInterfacesManager().viewGroup[guiId];
    }

    public void show(int dialogId, int dialogTypeId, String caption, String content, String leftBtnText, String rightBtnText) {
        if (viewGroup == null) {
            viewGroup = (ViewGroup) ((LayoutInflater) NvEventQueueActivity.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_old, (ViewGroup) null);
            NvEventQueueActivity.getInstance().getFrontUILayout().addView(viewGroup, -1, -1);
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 17;
        viewGroup.setLayoutParams(layoutParams);
        mCaption = (TextView) viewGroup.findViewById(R.id.dialog_caption);
        mContent = (TextView) viewGroup.findViewById(R.id.dialog_text);
        FrameLayout findViewById1 = viewGroup.findViewById(R.id.button_positive);
        mLeftBtn = findViewById1;
        FrameLayout findViewById2 = viewGroup.findViewById(R.id.button_negative);
        mRightBtn = findViewById2;
        mInputLayout = viewGroup.findViewById(R.id.dialog_input_layout);
        mListLayout = viewGroup.findViewById(R.id.dialog_list_layout);
        mMsgBoxLayout = (ConstraintLayout) viewGroup.findViewById(R.id.dialog_text_layout);
        mInput = (CustomEditText) viewGroup.findViewById(R.id.dialog_input);
        mCustomRecyclerView = (CustomRecyclerView) viewGroup.findViewById(R.id.dialog_list_recycler);
        findViewById1.setOnClickListener(view -> sendDialogResponse(1));
        findViewById2.setOnClickListener(view -> sendDialogResponse(0));
        this.mRowsList = new ArrayList<>();
        this.mHeadersList = new ArrayList<>();
        mInput.setShowSoftInputOnFocus(false);
        InputMethodManager inputMethodManager = (InputMethodManager) NvEventQueueActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && nvEventQueueActivity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(nvEventQueueActivity.getCurrentFocus().getWindowToken(), 0);
        }
        LinearLayout mHeadersLayout = viewGroup.findViewById(R.id.dialog_tablist_row);
        for (int i = 0; i < mHeadersLayout.getChildCount(); i++) {
            this.mHeadersList.add((TextView) mHeadersLayout.getChildAt(i));
        }
        this.mInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            Editable editableText;
            FrameLayout.LayoutParams layoutParamss = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParamss = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParamss.gravity = 49;
            viewGroup.setLayoutParams(layoutParamss);
            if ((i != 6 && i != 5) || (editableText = this.mInput.getText()) == null) {
                return false;
            }
            this.mCurrentInputText = editableText.toString();
            return false;
        });
        this.mInput.setOnClickListener(view ->
        {
            this.mInput.requestFocus();
        });

        clearDialogData();
        this.mCurrentDialogId = dialogId;
        this.mCurrentDialogTypeId = dialogTypeId;
        if (dialogTypeId == 0) {
            this.mInputLayout.setVisibility(View.GONE);
            this.mListLayout.setVisibility(View.GONE);
            this.mMsgBoxLayout.setVisibility(View.VISIBLE);
        }
        else if(dialogTypeId == 1 || dialogTypeId == 3)
        {
            this.mInputLayout.setVisibility(View.VISIBLE); // выполняется инпут
            this.mMsgBoxLayout.setVisibility(View.VISIBLE);
            this.mListLayout.setVisibility(View.GONE);
        }
        else
        {
            this.mInputLayout.setVisibility(View.GONE);
            this.mMsgBoxLayout.setVisibility(View.GONE); // LIST, TABLIST, TABLIST_HEADER
            this.mListLayout.setVisibility(View.VISIBLE);
            loadTabList(content);
            ArrayList<String> fixFieldsForDialog = Utils.fixFieldsForDialog(this.mRowsList);
            this.mRowsList = fixFieldsForDialog;
            DialogAdapter adapter = new DialogAdapter(fixFieldsForDialog, this.mHeadersList);
            adapter.setOnClickListener((i, str) -> { this.mCurrentListItem = i;
                this.mCurrentInputText = str; });
            adapter.setOnDoubleClickListener(() -> sendDialogResponse(1));
            this.mCustomRecyclerView.setLayoutManager(new LinearLayoutManager((Context) NvEventQueueActivity.getInstance()));
            this.mCustomRecyclerView.setAdapter(adapter);
            if (dialogTypeId != 2) {
                CustomRecyclerView customRecyclerView = this.mCustomRecyclerView;
                adapter.getClass();
                customRecyclerView.post(() -> adapter.updateSizes());
            }
        }
        this.mCaption.setText(Utils.transfromColors(caption));
        this.mContent.setText(Utils.transfromColors(content));
        ((TextView) this.mLeftBtn.getChildAt(0)).setText(Utils.transfromColors(leftBtnText));
        ((TextView) this.mRightBtn.getChildAt(0)).setText(Utils.transfromColors(rightBtnText));
        if (rightBtnText.equals("")) { this.mRightBtn.setVisibility(View.GONE); }
        else { this.mRightBtn.setVisibility(View.VISIBLE); }
        InterfacesManager.getInterfacesManager().AnimVisibale(viewGroup, View.VISIBLE);
    }

    public void gravity(int i) {
        FrameLayout.LayoutParams layoutParamss = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        layoutParamss = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        layoutParamss.gravity = i;
        viewGroup.setLayoutParams(layoutParamss);
    }

    public void hideWithoutReset() {
        InterfacesManager.getInterfacesManager().AnimVisibale(viewGroup, View.GONE);
    }

    public void showWithOldContent() {
        InterfacesManager.getInterfacesManager().AnimVisibale(viewGroup, View.VISIBLE);
    }

    public void sendDialogResponse(int btnId) {
        if (!this.mCurrentInputText.equals(this.mInput.getText().toString())) {
            this.mCurrentInputText = this.mInput.getText().toString();
        }
        ((InputMethodManager) NvEventQueueActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.mInput.getWindowToken(), 0);
        try {
            NvEventQueueActivity.getInstance().sendDialogResponse(btnId, this.mCurrentDialogId, this.mCurrentListItem, this.mCurrentInputText.getBytes("windows-1251"));
            InterfacesManager.getInterfacesManager().AnimVisibale(viewGroup, View.GONE);
            InterfacesManager.getInterfacesManager().getKeyBoardManager().close();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void loadTabList(String content) {
        String[] strings = content.split("\n");
        for (int i = 0; i < strings.length; i++) {
            if (this.mCurrentDialogTypeId == 5 && i == 0) {
                String[] headers = strings[i].split("\t");
                for (int j = 0; j < headers.length; j++) {
                    this.mHeadersList.get(j).setText(Utils.transfromColors(headers[j]));
                    this.mHeadersList.get(j).setVisibility(View.VISIBLE);
                }
            } else {
                this.mRowsList.add(strings[i]);
            }
        }
    }

    private void clearDialogData() {
        this.mInput.setText("");
        this.mCurrentDialogId = -1;
        this.mCurrentDialogTypeId = -1;
        this.mCurrentListItem = -1;
        this.mRowsList.clear();
        for (int i = 0; i < this.mHeadersList.size(); i++) {
            this.mHeadersList.get(i).setVisibility(View.GONE);
        }
    }

    public void onHeightChanged(int height) {
        if (viewGroup != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            params.setMargins(0, 0, 0, height);
            viewGroup.setLayoutParams(params);
        }
    }
}