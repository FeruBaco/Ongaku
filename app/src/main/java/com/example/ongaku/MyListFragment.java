package com.example.ongaku;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ongaku.adapter.ListAdapter;
import com.example.ongaku.entities.MyList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MyListFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ArrayList<MyList> myList;
    private ListAdapter adapter;
    private MaterialAlertDialogBuilder builder;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MyListFragment() {
        // Required empty public constructor
    }

    public static MyListFragment newInstance(String param1, String param2) {
        MyListFragment fragment = new MyListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_list, container, false);
        floatingActionButton = v.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
            }
        });
        myList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getList();
        adapter = new ListAdapter(myList, getActivity(), recyclerView);
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void addList(){
        View view = View.inflate(R.)
        builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.addlist_text_newlist))
                .
    builder.show();
    }

    private void getList(){

    }
}
