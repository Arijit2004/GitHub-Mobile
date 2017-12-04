package com.example.arijit.github_mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.model.SearchRepoDetails;
import com.example.arijit.github_mobile.model.UserRepoDetails;

import java.util.List;

/**
 * Created by arijit on 04/12/17.
 */

public class SearchRepoAdapter extends RecyclerView.Adapter<SearchRepoAdapter.Holder> {

    private List<SearchRepoDetails> itemList;
    private LayoutInflater inflater;

    public SearchRepoAdapter(Context context, List<SearchRepoDetails> itemList) {
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SearchRepoAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_repo_item_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchRepoAdapter.Holder holder, int position) {
        final SearchRepoDetails details = itemList.get(position);
        holder.name.setText("Name" + " - " + details.getName());
        holder.fullname.setText("Path" + " - " + details.getFullName());
        holder.type.setText("Private" + " - " + String.valueOf(details.isType()));
        holder.description.setText("Description" + " - " + details.getDescription());
        holder.language.setText("Language" + " - " + details.getLanguage());
        holder.watchers.setText("Watchers" + " - " + details.getWatchers());
        holder.forks.setText("Forks" + " - " + details.getForks());
        holder.stars.setText("Stars" + " - " + details.getStars());

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

        TextView language;

        TextView watchers;

        TextView forks;

        TextView stars;



        public Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_name);
            fullname = itemView.findViewById(R.id.search_fullname);
            type = itemView.findViewById(R.id.search_type);
            description = itemView.findViewById(R.id.search_description);
            language = itemView.findViewById(R.id.search_language);
            watchers = itemView.findViewById(R.id.search_watchers);
            forks = itemView.findViewById(R.id.search_forks);
            stars = itemView.findViewById(R.id.search_stars);
//            ButterKnife.bind(this, this.itemView);
        }
    }
}
