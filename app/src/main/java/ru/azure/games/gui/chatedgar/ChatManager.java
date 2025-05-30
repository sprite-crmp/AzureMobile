package ru.azure.games.gui.chatedgar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvidia.devtech.NvEventQueueActivity;

import java.util.ArrayList;

import ru.azure.games.gui.Speedometer;
import ru.azure.launcher.other.Lists;
import ru.azure.games.InterfacesManager;
import ru.azure.games.R;
import ru.azure.games.gui.util.CustomRecyclerView;
import ru.azure.games.gui.util.Utils;

public class ChatManager {
    public NvEventQueueActivity nvEventQueueActivity;
    private static ChatManager instance;
    public static int statusChat = 1;
    public ViewGroup viewGroup;
    public CustomRecyclerView msg_messages;
    public ArrayList<String> msglist = new ArrayList<>();
    public ChatAdapter chatAdapter;
    public Speedometer speedometer;
    public ImageView msg_box;
    public static boolean isChat = false;
    public int color1;
    public String texttempcolor;

    public native void sendChatMessages(byte[] messages);

    public ChatManager(NvEventQueueActivity nvEventQueueActivity, int guiId){
        this.nvEventQueueActivity = nvEventQueueActivity;
        viewGroup = InterfacesManager.getInterfacesManager().viewGroup[1];
        show();
        speedometer = new Speedometer(nvEventQueueActivity);
        //instance = this;
    }

    public void show() {
        if (viewGroup != null) {
            return;
        }
        viewGroup = (ViewGroup) ((LayoutInflater) NvEventQueueActivity.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat, (ViewGroup) null);
        NvEventQueueActivity.getInstance().getBackUILayout().addView(viewGroup, -1, -1);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        Resources resources;
        int i10;
        resources = NvEventQueueActivity.getInstance().getResources();
        layoutParams.width = -1;
        layoutParams.height = resources.getDimensionPixelSize(R.dimen._88sdp);
        layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen._110sdp);
        i10 = R.dimen._128sdp;
        layoutParams.rightMargin = resources.getDimensionPixelSize(i10);
        layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen._7sdp);
        viewGroup.setLayoutParams(layoutParams);

        msg_messages = viewGroup.findViewById(R.id.msg_messages);
        msg_box = viewGroup.findViewById(R.id.msg_box);

        msg_messages.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(nvEventQueueActivity, LinearLayoutManager.VERTICAL, false);
        msg_messages.setLayoutManager(layoutManager);

        msglist = Lists.msglist;
        chatAdapter = new ChatAdapter(nvEventQueueActivity, msglist);
        msg_messages.setAdapter(chatAdapter);
        msg_messages.setVerticalScrollBarEnabled(false);
        msg_messages.setEnableScrolling(false);
        msg_box.setAlpha(0.0f);
    }

    public final void ChatOpen(){
        msg_messages.setVerticalScrollBarEnabled(true);
        msg_messages.setEnableScrolling(true);
        if(statusChat == 3) {
            msg_messages.animate().alpha(1.0f).setDuration(300).start();
        }
        msg_box.animate().alpha(1.0f).setDuration(300).start();
        msg_box.clearAnimation();
        InterfacesManager.getInterfacesManager().getKeyBoardManager().OpenKeyBoard(null);
        isChat = true;
    }

    public final void ChatClose() {
        msg_messages.setVerticalScrollBarEnabled(false);
        msg_messages.setEnableScrolling(false);
        msg_messages.scrollToPosition(-1);
        if(statusChat == 3) {
            msg_messages.animate().alpha(0.0f).setDuration(300).start();
        }
        msg_box.animate().alpha(0.0f).setDuration(300).start();
        msg_box.clearAnimation();
        InterfacesManager.getInterfacesManager().getKeyBoardManager().q();
        isChat = false;
        speedometer.SpeedStop(0);
    }

    public void setChatStatys(int i){
        if(i == 1){
            msg_messages.animate().alpha(1.0f).setDuration(300).start();
            statusChat = i;
        } else if(i == 3){
            msg_messages.animate().alpha(0.0f).setDuration(300).start();
            statusChat = i;
        }
    }

    public void AddChatMessage(String msg, int color){
        String textcolor = String.format("%06X", color, msg.hashCode());
        texttempcolor = textcolor.substring(0,6);
        chatAdapter.addItem("{" + texttempcolor + "}" + msg);
    }

    public void AddDebugMessage(String msg){
        chatAdapter.addItem(msg);
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
        Context context;

        ArrayList<String> chat_message;


        public ChatAdapter(Context context, ArrayList<String> chat_message){
            this.context = context;
            this.chat_message = chat_message;
        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_message, parent, false);
            return new ChatViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            String pon = String.valueOf(chat_message.get(position));
            int index = pon.indexOf("{");
            int secondBraceIndex = pon.indexOf('{', pon.indexOf('{') + 1);

            if (secondBraceIndex != -1) {
                // Если найдено, выводим позицию второй фигурной скобки
                String firstEightChars = pon.substring(secondBraceIndex + 1, secondBraceIndex + 7);
                holder.msg_driver.setBackgroundColor(Color.parseColor("#" + firstEightChars));
                index = -1;
            } else {
                // Если не найдено, выводим сообщение об отсутствии второй скобки
                String firstEightChars = pon.substring(index + 1, index + 7);
                holder.msg_driver.setBackgroundColor(Color.parseColor("#" + firstEightChars));
                index = -1;
            }
            System.out.println(pon);
            holder.msg.setText(Utils.transfromColors(pon));
        }

        @Override
        public int getItemCount() {
            return chat_message.size();
        }

        public class ChatViewHolder extends RecyclerView.ViewHolder {

            public TextView msg;
            public View msg_driver;

            public ChatViewHolder(View itemView) {
                super(itemView);
                msg = itemView.findViewById(R.id.msg_text);
                msg_driver = itemView.findViewById(R.id.msg_divider);
                msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isChat == false) {
                            ChatOpen();
                            isChat = true;
                        } else {
                            ChatClose();
                            isChat = false;
                        }
                    }
                });
            }
        }

        public void addItem(String item) {
            nvEventQueueActivity.runOnUiThread(() -> {
                if(this.chat_message.size() > 40){
                    this.chat_message.remove(0);
                    notifyItemRemoved(0);
                }
                this.chat_message.add(" "+item+" ");
                notifyItemInserted(this.chat_message.size() - 1);
                if(!isChat) {
                    if (msg_messages.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                        msg_messages.scrollToPosition(this.chat_message.size() - 1);
                    }
                }
            });

        }

        public void scrollItem() {
            if(!isChat) {
                if (msg_messages.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    msg_messages.scrollToPosition(this.chat_message.size() - 1);
                }
            }
        }

    }
}
