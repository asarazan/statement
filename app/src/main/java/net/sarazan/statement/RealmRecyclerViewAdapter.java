package net.sarazan.statement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;

import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class RealmRecyclerViewAdapter<T extends RealmObject, VH extends ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected LayoutInflater inflater;
    protected RealmResults<T> realmResults;
    protected Context context;
    private final RealmChangeListener listener;

    public RealmRecyclerViewAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
        this.listener = (!automaticUpdate) ? null : new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        };

        if (listener != null && realmResults != null) {
            getRealm().addChangeListener(listener);
        }
    }

    public T getItem(int position) {
        if (realmResults == null) return null;
        return realmResults.get(position);
    }

    @Override
    public int getItemCount() {
        if (realmResults == null) {
            return 0;
        }
        return realmResults.size();
    }

    /**
     * Returns the current ID for an item. Note that item IDs are not stable so you cannot rely on
     * the item ID being the same after {@link #notifyDataSetChanged()} or
     * {@link #updateRealmResults(RealmResults)} has been called.
     *
     * @param i Index of item in the adapter
     * @return Current item ID.
     */
    @Override
    public long getItemId(int i) {
        // TODO: find better solution once we have unique IDs
        return i;
    }

    /**
     * Update the RealmResults associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature
     *
     * @param queryResults the new RealmResults coming from the new query.
     */
    public void updateRealmResults(RealmResults<T> queryResults) {
        if (listener != null) {
            // Making sure that Adapter is refreshed correctly if new RealmResults come from another Realm
            if (this.realmResults != null) {
                getRealm().removeChangeListener(listener);
            }
            if (queryResults != null) {
                getRealm(queryResults).addChangeListener(listener);
            }
        }

        this.realmResults = queryResults;
        notifyDataSetChanged();
    }

    public Realm getRealm() {
        return getRealm(this.realmResults);
    }

    private Realm getRealm(RealmResults results) {
        Method m = null;
        try {
            m = RealmResults.class.getDeclaredMethod("getRealm");
            m.setAccessible(true);
            return (Realm) m.invoke(results);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (m != null) m.setAccessible(false);
        }
    }
}
