package com.laioffer.tinnews.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.databinding.SavedNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.R;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>{

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }


    // 2. SavedNewsViewHolder:
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;

        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
            favoriteIcon = binding.savedItemFavoriteImageView;
        }
    }


    // 3. Adapter overrides:
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new SavedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Toast.makeText(holder.itemView.getContext(), "Deleted~", Toast.LENGTH_SHORT).show();
                itemCallback.onRemoveFavorite(article);
                //    fragment.deleteArticle(article);
                //    viewModel.deleteSavedArticle(article);
            }
        });
        holder.itemView.setOnClickListener(
                v -> itemCallback.onOpenDetails(article)
        );
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    //** Define an ItemCallback interface, and a private instance of the listener, and a setter for it.

    // bad way -> coupling example:
    //   private SaveViewModel viewModel;
    //   private SaveFragment fragment;
    //
    //   public void setViewModel(SaveViewModel viewModel) {
    //       this.viewModel = viewModel;
    //   }
    //
    //   public void setFragment(SaveFragment fragment) {
    //       this fragment = fragment
    //   }
    interface ItemCallback {

        void onOpenDetails(Article article);
        void onRemoveFavorite(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }
}
