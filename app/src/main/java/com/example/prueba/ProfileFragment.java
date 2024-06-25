package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements CardWorkAdapter.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<WorkItem> workItemList = new ArrayList<>();
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "App Design", "UX/UI Design", 50, R.drawable.ic_appdesign));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Web Design", "UX/UI Design", 100, R.drawable.ic_webdesign));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Visual Identity", "Branding", 25, R.drawable.ic_visualidentity));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Manual Design", "Editorial", 75, R.drawable.ic_manualdesign));
        CardWorkAdapter workAdapter = new CardWorkAdapter(workItemList, this);
        recyclerView.setAdapter(workAdapter);
        return view;
    }

    @Override
    public void onItemClick(WorkItem workItem) {
        Intent intent = new Intent(getContext(), WorkDetail.class);
        startActivity(intent);
    }
}
