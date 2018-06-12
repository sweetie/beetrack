package beetrack.com.news.mvp.common.interfaces;

/**
 * Created by Enny Querales
 */

public interface Listener {

    interface OnNetworkResponseListener {
        <T> void processResponse(T response);

        <T> void processFailure(T response);
    }

    interface ExecutorListener {
        void execute(String name);
    }

    interface OnFavoriteSelectedListener {
        <T> void onSelected(T model);
    }
}
