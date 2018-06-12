package beetrack.com.news.mvp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Enny Querales
 */

@DatabaseTable
public class ArticleModel
        implements Parcelable {

    public static final String ID = "_id";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";
    public static final String URL_TO_IMAGE = "url_to_image";
    public static final String PUBLISHED_AT = "published_at";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = AUTHOR)
    @SerializedName("author")
    private String author;

    @DatabaseField(columnName = TITLE)
    @SerializedName("title")
    private String title;

    @DatabaseField(columnName = DESCRIPTION)
    @SerializedName("description")
    private String description;

    @DatabaseField(columnName = URL)
    @SerializedName("url")
    private String url;

    @DatabaseField(columnName = URL_TO_IMAGE)
    @SerializedName("urlToImage")
    private String urlToImage;

    @DatabaseField(columnName = PUBLISHED_AT)
    @SerializedName("publishedAt")
    private String publishedAt;

    public ArticleModel(){

    }

    protected ArticleModel(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleModel> CREATOR = new Creator<ArticleModel>() {
        @Override
        public ArticleModel createFromParcel(Parcel in) {
            return new ArticleModel(in);
        }

        @Override
        public ArticleModel[] newArray(int size) {
            return new ArticleModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ArticleModel)) {
            return false;
        }
        ArticleModel articleModel = (ArticleModel) o;
        return articleModel.getUrl().equals(getUrl());
    }

    public int hashCode() {
        return getUrl().hashCode();
    }
}
