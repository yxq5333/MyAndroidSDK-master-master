package com.crazyhuskar.myandroidsdk.view.dialogfragment.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * @author Timmy
 * @time 2018/1/24 14:39
 * @GitHub https://github.com/Timmy-zzh/MyDialogFragment
 **/
public abstract class ListDialogAdapter<T> extends RecyclerView.Adapter<BindViewHolder> {

    private final int layoutRes;
    private List<T> datas;
    private OnAdapterItemClickListener adapterItemClickListener;
    private MyDialogFragment dialog;

    protected abstract void onBind(BindViewHolder holder, int position, T t);

    public ListDialogAdapter(@LayoutRes int layoutRes, List<T> datas) {
        this.layoutRes = layoutRes;
        this.datas = datas;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(final BindViewHolder holder, final int position) {
        onBind(holder, position, datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemClickListener.onItemClick(holder, position, datas.get(position), dialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setTDialog(MyDialogFragment myDialogFragment) {
        this.dialog = myDialogFragment;
    }

    public interface OnAdapterItemClickListener<T> {
        void onItemClick(BindViewHolder holder, int position, T t, MyDialogFragment myDialogFragment);
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.adapterItemClickListener = listener;
    }
}
