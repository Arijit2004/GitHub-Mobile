package com.example.arijit.github_mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.model.UserRepoDetails;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by arijit on 04/12/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.Holder> {

    private List<UserRepoDetails> itemList;
    private LayoutInflater inflater;

    public RepoAdapter(Context context, List<UserRepoDetails> itemList) {
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RepoAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.repo_item_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RepoAdapter.Holder holder, int position) {
        final UserRepoDetails details = itemList.get(position);
        holder.name.setText(holder.name.getText() + " - " + details.getName());
        holder.fullname.setText("Path" + " - " + details.getFullName());
        holder.type.setText("Private" + " - " + String.valueOf(details.isType()));
        holder.description.setText(holder.description.getText() + " - " + details.getDescription());
        holder.licence.setText(holder.licence.getText() + " - " + details.getLicense());
        holder.language.setText(holder.language.getText() + " - " + details.getLanguage());
        holder.watchers.setText(holder.watchers.getText() + " - " + details.getWatchers());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    static class Holder extends RecyclerView.ViewHolder {
        TextView name;

        TextView fullname;

        TextView type;

        TextView description;

        TextView licence;

        TextView language;

        TextView watchers;



        public Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            fullname = itemView.findViewById(R.id.fullname);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            licence = itemView.findViewById(R.id.license);
            language = itemView.findViewById(R.id.language);
            watchers = itemView.findViewById(R.id.watchers);
//            ButterKnife.bind(this, this.itemView);
        }
    }
}
