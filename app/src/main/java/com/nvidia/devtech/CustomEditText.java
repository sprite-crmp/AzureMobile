package com.nvidia.devtech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

import ru.azure.games.InterfacesManager;
import ru.azure.games.gui.keyboard.KeyBoard;

@SuppressLint({"AppCompatCustomView"})
public class CustomEditText extends EditText {

    public c f4348a;

    public d f4349b;
    public final ArrayList<OnFocusChangeListener> c;

    public class a implements OnFocusChangeListener {
        public a() {
        }

        @Override
        public final void onFocusChange(View view, boolean z6) {
            for (int i10 = 0; i10 < CustomEditText.this.c.size(); i10++) {
                CustomEditText.this.c.get(i10).onFocusChange(view, z6);
            }
        }
    }

    public class b implements OnFocusChangeListener {
        public b() {
        }

        @Override
        public final void onFocusChange(View view, boolean z6) {
            if (z6) {
                d dVar = CustomEditText.this.f4349b;
                if (dVar != null) {
                    dVar.a();
                } else {
                    InterfacesManager.getInterfacesManager().getKeyBoardManager().OpenKeyBoard(CustomEditText.this);
                }
            }
        }
    }

    public interface c {
    }

    public interface d {
        void a();
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4348a = null;
        this.f4349b = null;
        ArrayList<OnFocusChangeListener> arrayList = new ArrayList<>();
        this.c = arrayList;
        setOnFocusChangeListener(new a());
        setShowSoftInputOnFocus(false);
        if (getTag() == null || !getTag().equals("keyboard_input")) {
            arrayList.add(new b());
        }
    }

    public final void a(OnFocusChangeListener onFocusChangeListener) {
        this.c.remove(onFocusChangeListener);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onKeyPreIme(int i10, KeyEvent keyEvent) {
        c cVar;
        if (i10 != 4 || (cVar = this.f4348a) == null) {
            return false;
        }
        if (!InterfacesManager.getInterfacesManager().getKeyBoardManager().isChatClose) {
            InterfacesManager.getInterfacesManager().getKeyBoardManager().x();
        } else {
            InterfacesManager.getInterfacesManager().getKeyBoardManager().q();
        }
        return true;
    }

    public void setOnBackListener(c cVar) {
        this.f4348a = cVar;
    }

    public void setOnKeyboardOpenListener(d dVar) {
        this.f4349b = dVar;
    }
}
