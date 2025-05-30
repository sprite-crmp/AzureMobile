package ru.azure.launcher.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.azure.launcher.other.Utils;
import ru.azure.games.BuildConfig;
import ru.azure.games.R;
import ru.azure.launcher.activity.MainActivity;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class SettingsFragment extends Fragment {

    Animation animation;
    public EditText nickname;
    String nickName;
    TextView faq_text, account_not_auth_text, account_text;
    Timer i;
    public static SettingsFragment in;

    public static SettingsFragment getInSettings(){return in;}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);
        in = this;
        i = new Timer();

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);

        inflate.findViewById(R.id.btn_close).setOnClickListener(v -> {
            v.startAnimation(animation);
            //i.cancel();
            MainActivity.getMainActivity().closeServers();
        });

        nickname = (EditText) inflate.findViewById(R.id.account_text);
        account_text = (TextView) inflate.findViewById(R.id.account_text);
        faq_text = (TextView) inflate.findViewById(R.id.faq_text);
        account_not_auth_text = (TextView) inflate.findViewById(R.id.account_not_auth_text);

        InitLogic();
        Version();

        ((FrameLayout) inflate.findViewById(R.id.btn_reinstall_data))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                Toast.makeText(v.getContext(),"Вы успешно удалили файлы, теперь переустановите игру", Toast.LENGTH_LONG).show();
                                Toast.makeText(v.getContext(),"Не забудьте перезапустить лаунчер", Toast.LENGTH_LONG).show();
                                File gameDirectory = (new File("storage/emulated/0/Android/data/ru.azure.games/files/"));
                                Utils.delete(gameDirectory);
                                gameDirectory.mkdirs();
                            }
                        });
        ((FrameLayout) inflate.findViewById(R.id.btn_reinstall_dev))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip"));
                                Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip.temp"));
                                Toast.makeText(v.getContext(),"Вы успешно удалили кеш загрузки", Toast.LENGTH_SHORT).show();
                            }
                        });
        ((FrameLayout) inflate.findViewById(R.id.btn_reinstall_client))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

        ((EditText) nickname)
                .setOnEditorActionListener(
                        new EditText.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(
                                    TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_SEARCH
                                        || actionId == EditorInfo.IME_ACTION_DONE
                                        || event.getAction() == KeyEvent.ACTION_DOWN
                                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                    try {
                                        File f = new File(requireContext().getExternalFilesDir(null), "SAMP/settings.ini");
                                        if (!f.exists()) {
                                            f.createNewFile();
                                            f.mkdirs();

                                        }
                                        Wini w =
                                                new Wini(
                                                        new File(
                                                                requireContext().getExternalFilesDir(null), "SAMP/settings.ini"));
								 if(checkValidNick(inflate)){
									 w.put("client", "name", nickname.getText().toString());
                                        Toast.makeText(
                                                getActivity(),
                                                "Ваш новый никнейм успешно сохранен!",
                                                Toast.LENGTH_SHORT).show();
                                     DownloadFragment.nick = nickname.getText().toString();
								 } else {
									 checkValidNick(inflate);
								 }
                                        w.store();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        DownloadFragment.nick = nickname.getText().toString();
										//Toast.makeText(getActivity(), "Установите игру!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(
                                                getActivity(),
                                                "Ваш новый никнейм успешно сохранен!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
        });
        return inflate;
    }

    private void InitLogic() {
        try {
            Wini w = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/settings.ini"));
            nickname.setText(w.get("client", "name"));
            w.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Version() {
        try {
            Wini v = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/data/script/server.ini"));
            String ver_cache = v.get("check", "ver_cache");
            String ver_apk = BuildConfig.VERSION_NAME;

            //faq_text.setText("cache = " + ver_cache + "\napk = " + ver_apk);

            String numberStr = v.get("check", "ver_cache");
            int number = Integer.parseInt(numberStr);
            System.out.println("Версия кеша: " + number);

            v.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean checkValidNick(View inflate){
		EditText nick = (EditText) inflate.findViewById(R.id.account_text);
		if(nick.getText().toString().isEmpty()) {
			Toast.makeText(getActivity(), "Введите ник", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!(nick.getText().toString().contains("_"))){
			Toast.makeText(getActivity(), "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
            account_text.setText("");
			return false;
		}
		if(nick.getText().toString().length() < 4){
			Toast.makeText(getActivity(), "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
            account_text.setText("");
			return false;
		}
		return true;
	}
}