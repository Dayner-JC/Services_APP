package dev.godjango.apk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment implements CardWorkAdapter.OnItemClickListener, CardWorkAdapter.OnDeleteClickListener, PopUpDeleteWork.OnDeleteWorkListener {

    private CardWorkAdapter workAdapter;
    private static final int REQUEST_DELETE_WORK = 1;
    private List<WorkItem> workItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.work, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        WorkViewModel workViewModel = new ViewModelProvider(requireActivity()).get(WorkViewModel.class);

        workViewModel.newCardData.observe(getViewLifecycleOwner(), newCardData -> {
            if (newCardData != null) {
                newCard(newCardData);
                workViewModel.newCardData.setValue(null);
            }
        });

        workItemList = new ArrayList<>();

        workAdapter = new CardWorkAdapter(workItemList, this, this);
        recyclerView.setAdapter(workAdapter);

        return view;
    }

    public void newCard(Bundle newCardData){

        String title = newCardData.getString("Title");
        String category = newCardData.getString("Category");

            WorkItem newWorkItem = new WorkItem(R.drawable.ic_papelera, "Progress", "Apr 30. 2024", title, category, 0, getImageResourceForTitle(category,title), newCardData);
            workItemList.add(newWorkItem);
            workAdapter.notifyItemInserted(workItemList.size() - 1);

    }

    private int getImageResourceForTitle(String category,String title) {
        switch (category) {
            case "Branding":
                return R.drawable.ic_branding;
            case "Services":
                return R.drawable.ic_services;
            case "Interface":
                if(title.contains("Web")){
                return R.drawable.ic_webdesign;
                }else{
                    return R.drawable.ic_appdesign;
                }
            case "Editorial":
                return R.drawable.ic_editorial;
            default:
                return 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        WorkViewModel workViewModel = new ViewModelProvider(requireActivity()).get(WorkViewModel.class);

        workViewModel.newCardData.observe(getViewLifecycleOwner(), newCardData -> {
            if (newCardData != null) {
                newCard(newCardData);
                workViewModel.newCardData.setValue(null);
            }
        });
    }

    @Override
    public void onItemClick(WorkItem workItem) {
        Intent intent = new Intent(getContext(), WorkDetail.class);
        intent.putExtra("itemPosition", workItemList.indexOf(workItem));
        intent.putExtra("Title",workItem.getTitle());
        intent.putExtra("Description",workItem.getCategory());
        intent.putExtra("Progress",workItem.getProgress());
        intent.putExtra("Time",workItem.getDate());
        intent.putExtra("CardData", workItem.getCardData());
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
