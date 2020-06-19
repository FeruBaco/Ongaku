package com.example.ongaku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ongaku.R;
import com.example.ongaku.entities.MyList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    ArrayList<MyList> myList;
    Context mContext;
    private RecyclerView mRecyclerView;

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        public View layout;

        public ListViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            title = itemView.findViewById(R.id.list_title);
            date = itemView.findViewById(R.id.list_date);
        }
    }

    public ListAdapter(ArrayList<MyList> myList, Context context, RecyclerView recyclerView){
        this.myList = myList;
        mContext = context;
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist_item, null, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, final int position) {
        final MyList list = myList.get(position);

        holder.title.setText(myList.get(position).getName().toString());
        holder.date.setText(myList.get(position).getCreationDate().toString().substring(0,16));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
