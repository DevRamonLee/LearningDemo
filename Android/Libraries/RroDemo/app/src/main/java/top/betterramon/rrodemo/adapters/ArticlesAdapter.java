package top.betterramon.rrodemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.betterramon.rrodemo.R;
import top.betterramon.rrodemo.net.beans.ArticlesSubject;

/**
 * Created by Ramon Lee on 2019/8/9.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>{
    private ArticlesSubject articles;
    private Context mContext;

    public ArticlesAdapter(Context context,ArticlesSubject articles) {
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
        holder.articleTitle.setText(articles.getData().getDatas().get(position).getTitle());
        holder.superChapterName.setText(articles.getData().getDatas().get(position).getChapterName());
    }

    @Override
    public int getItemCount() {
        return articles.getData().getDatas().size();
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
