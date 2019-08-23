package top.betterramon.rrodemo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.betterramon.rrodemo.R;
import top.betterramon.rrodemo.beans.ArticlesBean;

/**
 * Created by Ramon Lee on 2019/8/9.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>{
    private Context mContext;

    private List<ArticlesBean.ArticleInfo> articles;

    public ArticlesAdapter(Context context, List<ArticlesBean.ArticleInfo> articles) {
        this.mContext = context;
        this.articles = articles;
    }
    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_article_item,parent, false );
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        holder.articleTitle.setText(articles.get(position).getTitle());
        holder.superChapterName.setText(articles.get(position).getChapterName());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticlesViewHolder extends RecyclerView.ViewHolder {
        private TextView articleTitle;
        private TextView superChapterName;

        public  ArticlesViewHolder(View view) {
            super(view);
            articleTitle = view.findViewById(R.id.article_title);
            superChapterName = view.findViewById(R.id.super_chapter_Name);
        }
    }
}
