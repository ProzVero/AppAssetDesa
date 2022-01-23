package com.jumarni.appassetdesa.presentasi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jumarni.appassetdesa.R;
import com.jumarni.appassetdesa.api.URLServer;
import com.jumarni.appassetdesa.model.Namakk;
import com.jumarni.appassetdesa.view.activity.DetailKkActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class DetailPendudukAdapter extends RecyclerView.Adapter<DetailPendudukAdapter.HolderData> {
    private final Context context;
    private final ArrayList<Namakk> namakks;
    Filter searchData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Namakk> searchList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                searchList.addAll(namakks);
            } else {
                for (Namakk getnama : namakks) {
                    if (getnama.getNama_kk().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        searchList.add(getnama);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = searchList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            namakks.clear();
            namakks.addAll((Collection<? extends Namakk>) results.values);
            notifyDataSetChanged();
        }
    };
    private int kk_id;
    private StringRequest getPenduduk;

    public DetailPendudukAdapter(Context context, ArrayList<Namakk> namakks) {
        this.context = context;
        this.namakks = namakks;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_jml_art, parent, false);
        return new HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {

        Namakk datanama = namakks.get(position);
        holder.nama.setText(datanama.getNama_kk());

        if (!datanama.getNik().equals("")){
            holder.nik.setText(datanama.getNik());
        }else {
            holder.nik.setText("-");
        }

        if (!datanama.getNo_kk().equals("")){
            holder.no_kk.setVisibility(View.VISIBLE);
            holder.no_kk.setText("Nomer KK : "+ datanama.getNo_kk());
        }else {
            holder.no_kk.setVisibility(View.GONE);
        }

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailKkActivity.class);
            intent.putExtra("kk_id", datanama.getId_kk());
            context.startActivity(intent);
            Log.d("Respon", "Data: " + datanama.getId_kk());
        });
        kk_id = datanama.getId_kk();

        if (kk_id != 0) {
            getPenduduk = new StringRequest(Request.Method.GET, URLServer.GETJMLART
                    + kk_id, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getString("data").matches("0")) {
                        holder.jml_art.setText(object.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
            RequestQueue koneksi = Volley.newRequestQueue(context);
            koneksi.add(getPenduduk);
        }

    }

    @Override
    public int getItemCount() {
        return namakks.size();
    }

    public Filter getSearchData() {
        return searchData;
    }

    public static class HolderData extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView nik;
        private final TextView no_kk;
        private final TextView jml_art;
        private final RelativeLayout layout;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            nik = itemView.findViewById(R.id.nik);
            no_kk = itemView.findViewById(R.id.no_kk);
            jml_art = itemView.findViewById(R.id.jml_art);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
