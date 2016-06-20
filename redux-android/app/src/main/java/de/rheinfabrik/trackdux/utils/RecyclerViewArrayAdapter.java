package de.rheinfabrik.trackdux.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class RecyclerViewArrayAdapter<TData, TView extends View> extends RecyclerView.Adapter<RecyclerViewArrayAdapter.ViewHolder<TView>> {

    // Members

    public List<TData> mData;

    // Constructor

    public RecyclerViewArrayAdapter(List<TData> pData) {
        super();

        mData = pData;
    }

    // Abstract Api

    public abstract TView onCreateView(Context pContext);

    public abstract void onBindData(TView pView, TData pData);

    // Adapter

    @Override
    public ViewHolder<TView> onCreateViewHolder(ViewGroup pParent, int pViewType) {
        return new ViewHolder<>(onCreateView(pParent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder<TView> pHolder, int pPosition) {
        onBindData(pHolder.getView(), mData.get(pPosition));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    // ViewHolder

    protected static class ViewHolder<TView extends View> extends RecyclerView.ViewHolder {

        // Constructor

        public ViewHolder(TView pItemView) {
            super(pItemView);
        }

        // Public Api

        @SuppressWarnings("unchecked")
        public TView getView() {
            return (TView) itemView;
        }
    }
}
