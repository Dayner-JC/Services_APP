package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/** @noinspection deprecation, deprecation */
public class ProfileFragment extends Fragment implements CardWorkAdapter.OnItemClickListener, CardWorkAdapter.OnDeleteClickListener, PopUpDeleteWork.OnDeleteWorkListener {

    private CardWorkAdapter workAdapter;
    private static final int REQUEST_DELETE_WORK = 1;
    private List<WorkItem> workItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        workItemList = new ArrayList<>();
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "App Design", "UX/UI Design", 50, R.drawable.ic_appdesign));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Web Design", "UX/UI Design", 100, R.drawable.ic_webdesign));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Visual Identity", "Branding", 25, R.drawable.ic_visualidentity));
        workItemList.add(new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", "Manual Design", "Editorial", 75, R.drawable.ic_manualdesign));

        workAdapter = new CardWorkAdapter(workItemList, this, this);
        recyclerView.setAdapter(workAdapter);

        return view;
    }

    @Override
    public void onItemClick(WorkItem workItem) {
        Intent intent = new Intent(getContext(), WorkDetail.class);
        intent.putExtra("itemPosition", workItemList.indexOf(workItem));
        intent.putExtra("Titulo",workItem.getTitle());
        intent.putExtra("Descripcion",workItem.getSubtitle());
        intent.putExtra("Progreso",workItem.getProgress());
        intent.putExtra("Tiempo",workItem.getDate());
        startActivityForResult(intent, REQUEST_DELETE_WORK);
    }

    @Override
    public void onDeleteClick(WorkItem workItem, int position) {
        showPopUp(position);
    }

    private void showPopUp(int position) {
        PopUpDeleteWork popUp = PopUpDeleteWork.newInstance(position, this);
        popUp.setCancelable(false);
        popUp.show(getParentFragmentManager(), "popup_window");
    }

    @Override
    public void onDeleteWork(int position) {
        workAdapter.removeItem(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DELETE_WORK && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            int deletePosition = data.getIntExtra("deletePosition", -1);
            if (deletePosition != -1) {
                workAdapter.removeItem(deletePosition);
            }
        }
    }
}
