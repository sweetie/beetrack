package beetrack.com.news.mvp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beetrack.com.news.mvp.model.data.ArticleModel;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "beetrack_news.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<ArticleModel, Integer> articleDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ArticleModel.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<ArticleModel, Integer> getArticleDao() throws SQLException {
        if (articleDao == null) {
            articleDao = getDao(ArticleModel.class);
        }
        return articleDao;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(ArticleModel obj) throws SQLException {
        articleDao = getArticleDao();
        return articleDao.createOrUpdate(obj);
    }

    @Override
    public void close() {
        super.close();
        articleDao = null;
    }


}