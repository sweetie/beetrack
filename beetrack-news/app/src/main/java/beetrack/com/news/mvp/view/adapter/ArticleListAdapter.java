package beetrack.com.news.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

import beetrack.com.news.R;
import beetrack.com.news.mvp.Beetrack;
import beetrack.com.news.mvp.common.interfaces.Listener;
import beetrack.com.news.mvp.model.DBHelper;
import beetrack.com.news.mvp.model.data.ArticleModel;
import beetrack.com.news.mvp.view.activities.WebViewActivity;
import beetrack.com.news.mvp.view.custom.CustomTextView;

/**
 * Created by Enny Querales
 */
public class ArticleListAdapter
        extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private List<ArticleModel> articles;
    private Listener.OnFavoriteSelectedListener listener;
    private boolean isFavorite;

    public ArticleListAdapter(Context context, List<ArticleModel> articles, boolean isFavorite) {
        this.context = context;
        this.articles = articles;
        this.isFavorite = isFavorite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ArticleModel articleModel = articles.get(position);

        Picasso.with(context).load(articleModel.getUrlToImage()).placeholder(R.color.black).resize(400, 400).into(holder.articleImageView);
        holder.authorTextView.setText(articleModel.getAuthor());
        holder.titleTextView.setText(articleModel.getTitle());
        holder.descriptionTextView.setText(articleModel.getDescription());
        holder.articleFavoriteImageView.setVisibility(isFavorite ? View.GONE : View.VISIBLE);

        holder.articleFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelected(articleModel);
                    holder.articleFavoriteImageView.setSelected(true);
                }
            }
        });

        holder.articleFavoriteImageView.setSelected(Beetrack.getArticles().contains(articleModel) ? true : false);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void clear() {
        int size = articles.size();
        articles.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void add(ArticleModel articleModel) {
        articles.add(articleModel);
        notifyDataSetChanged();
    }

    public void setOnFavoriteSelectedListener(Listener.OnFavoriteSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * View holder class for list item
     */
    class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /**
         * Attributes
         */
        ImageView articleImageView;
        CustomTextView authorTextView;
        CustomTextView titleTextView;
        CustomTextView descriptionTextView;
        ImageView articleFavoriteImageView;

        ViewHolder(View view) {
            super(view);
            articleImageView = (ImageView) view.findViewById(R.id.image_article);
            authorTextView = (CustomTextView) view.findViewById(R.id.author_article);
            titleTextView = (CustomTextView) view.findViewById(R.id.title_article);
            descriptionTextView = (CustomTextView) view.findViewById(R.id.description_article);
            articleFavoriteImageView = (ImageView) view.findViewById(R.id.favorite_article);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", articles.get(position).getUrl());
            context.startActivity(intent);
        }
    }
}
