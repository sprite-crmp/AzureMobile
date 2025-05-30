package ru.azure.launcher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.azure.games.R;
import ru.azure.launcher.activity.MainActivity;
import ru.azure.launcher.adapter.ServersAdapter;
import ru.azure.launcher.model.Servers;
import ru.azure.launcher.other.Lists;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ServerSelectFragment extends Fragment {

    ImageView close;

    RecyclerView recyclerServers;
    DatabaseReference databaseServers;
    ServersAdapter serversAdapter;
    ArrayList<Servers> slist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_server_select, container, false);

        close = inflate.findViewById(R.id.btn_exit);
        close.setOnClickListener(v -> {
            MainActivity.getMainActivity().closeServers();
        });

        recyclerServers = inflate.findViewById(R.id.serverlist_recycler);
		recyclerServers.setHasFixedSize(true);
		LinearLayoutManager layoutManagerr = new LinearLayoutManager(getActivity());
		recyclerServers.setLayoutManager(layoutManagerr);

		this.slist = Lists.slist;
		serversAdapter = new ServersAdapter(getContext(), this.slist);
		recyclerServers.setAdapter(serversAdapter);

        return inflate;
    }
}
