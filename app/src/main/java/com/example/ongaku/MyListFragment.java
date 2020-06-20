package com.example.ongaku;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ongaku.adapter.ListAdapter;
import com.example.ongaku.entities.MyList;
import com.example.ongaku.utilities.SQLiteHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MyListFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private SQLiteHelper conn;
    private ArrayList<MyList> myList;
    private ListAdapter adapter;
    private MaterialAlertDialogBuilder builder;
    private EditText dialogNameList;

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
        dialogNameList = v.findViewById(R.id.et_addlist_name);
        floatingActionButton = v.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
            }
        });
        conn = new SQLiteHelper(getActivity());
        myList = new ArrayList<MyList>();
        recyclerView = v.findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getList();
        adapter = new ListAdapter(myList, getActivity(), recyclerView);
        recyclerView.setAdapter(adapter);
        refreshRecycler();
        return v;
    }

    private void addList() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlist_dialog, null);
        builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.addlist_text_newlist))
                .setView(dialogView)
                .setNegativeButton(getResources().getString(R.string.addlist_text_cancel), null)
                .setPositiveButton(getResources().getString(R.string.addlist_text_save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogNameList = dialogView.findViewById(R.id.et_addlist_name);
                        addListPartTwo(dialogNameList.getText().toString());
                    }
                });
        builder.show();
    }

    private void addListPartTwo(String listName) {
        SQLiteHelper conn = new SQLiteHelper(getActivity());
        Date currentTime = Calendar.getInstance().getTime();

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", listName);
        values.put("date", currentTime.toString());

        Long idResult = db.insert("list", "id", values);
        Toast.makeText(getActivity(), "Se guardo correctamente " + idResult, Toast.LENGTH_SHORT).show();
        refreshRecycler();
        db.close();
    }

    private void getList() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list", null);

        while (cursor.moveToNext()) {
            MyList list = new MyList();
            list.setId(cursor.getInt(0));
            list.setName(cursor.getString(1));
            list.setCreationDate(cursor.getString(2));
            myList.add(list);
        }
            Log.d("ALGO",String.valueOf(myList.get(0).getName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void refreshRecycler() {
        myList.clear();
        getList();
        adapter = new ListAdapter(myList, getActivity(), recyclerView);
        recyclerView.setAdapter(adapter);
    }
}
