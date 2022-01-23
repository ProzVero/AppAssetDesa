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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jumarni.appassetdesa.R;
import com.jumarni.appassetdesa.api.URLServer;
import com.jumarni.appassetdesa.model.Dusun;
import com.jumarni.appassetdesa.view.activity.DetailPendudukActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class PendudukAdapter extends RecyclerView.Adapter<PendudukAdapter.HolderData> {
    private final Context context;
    private final ArrayList<Dusun> dusuns;
    Filter searchData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Dusun> searchList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                searchList.addAll(dusuns);
            } else {
                for (Dusun getdatapenduduk : dusuns) {
                    if (getdatapenduduk.getNama_dusun().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        searchList.add(getdatapenduduk);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = searchList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dusuns.clear();
            dusuns.addAll((Collection<? extends Dusun>) results.values);
            notifyDataSetChanged();
        }
    };
    private StringRequest getPenduduk, getPendudu2;
    private int dusun_id;

    public PendudukAdapter(Context context, ArrayList<Dusun> dusuns) {
        this.context = context;
        this.dusuns = dusuns;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_penduduk, parent, false);
        return new HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {

        Dusun dusun = dusuns.get(position);

        holder.dusun_id.setText(String.valueOf(dusun.getId_dusun()));
        holder.txt_dusun.setText("Dusun " + dusun.getNama_dusun());
        Log.d("Respon", "Data: " + dusun.getId_dusun());
        dusun_id = Integer.parseInt(holder.dusun_id.getText().toString());

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPendudukActivity.class);
            intent.putExtra("dusun_id", dusun.getId_dusun());
            context.startActivity(intent);
            Log.d("Respon", "Data ID DUSUN: " + dusun.getId_dusun());
        });

        if (dusun_id != 0) {
            getPenduduk = new StringRequest(Request.Method.GET, URLServer.GETJMLPERRT +
                    dusun_id, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("status")) {
                        holder.jml_rt.setText(object.getString("data"));
                    } else {
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
            RequestQueue koneksi = Volley.newRequestQueue(context);
            koneksi.add(getPenduduk);

            getPendudu2 = new StringRequest(Request.Method.GET, URLServer.GETJMLKK +
                    dusun_id, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("status")) {
                        holder.jml_penduduk.setText(object.getString("data"));
                    } else {
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
            RequestQueue koneksi2 = Volley.newRequestQueue(context);
            koneksi2.add(getPendudu2);
        }
    }

    @Override
    public int getItemCount() {
        return dusuns.size();
    }

    public Filter getSearchData() {
        return searchData;
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private final TextView jml_rt;
        private final TextView jml_penduduk;
        private final TextView txt_dusun;
        private final TextView dusun_id;
        private final RelativeLayout layout;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            txt_dusun = itemView.findViewById(R.id.nama_dusun);
            jml_rt = itemView.findViewById(R.id.jml_rt);
            jml_penduduk = itemView.findViewById(R.id.jml_penduduk);
            dusun_id = itemView.findViewById(R.id.dusun_id);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
