package com.jumarni.appassetdesa.presentasi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.jumarni.appassetdesa.R;
import com.jumarni.appassetdesa.helper.FormatRupiah;
import com.jumarni.appassetdesa.model.DataAssetDesaModel;
import com.jumarni.appassetdesa.view.activity.DaftarAsset;
import com.jumarni.appassetdesa.view.activity.SewaActivity;

import java.util.ArrayList;

public class DaftarAssetAdapter extends RecyclerView.Adapter<DaftarAssetAdapter.HolderData> {
    private final Context context;
    private final ArrayList<DataAssetDesaModel> databarang;
    private StringRequest updateAset;

    public DaftarAssetAdapter(Context context, ArrayList<DataAssetDesaModel> databarang) {
        this.context = context;
        this.databarang = databarang;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_aset, parent, false);
        return new HolderData(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {

        DataAssetDesaModel barang = databarang.get(position);

        FormatRupiah formatRupiah = new FormatRupiah();
        String harga = barang.getHarga();
        String hasil = formatRupiah.formatRupiah(Double.parseDouble(harga));

        holder.txt_hargaaset.setText(hasil);
        holder.txt_namaaset.setText(barang.getNama_aset());
        holder.jumlah_asset.setText(barang.getJml_aset() + " Barang tersedia");
        holder.txt_asetid.setText(String.valueOf(barang.getId_aset()));

        holder.relativeLayout.setOnClickListener(v -> {
            Intent i = new Intent(context, SewaActivity.class);
            i.putExtra("id_aset", barang.getId_aset());
            i.putExtra("nama_aset", barang.getNama_aset());
            i.putExtra("harga_aset", barang.getHarga());
            i.putExtra("jml_aset", barang.getJml_aset());
            context.startActivity(i);
            ((DaftarAsset) context).finish();
        });

    }

    @Override
    public int getItemCount() {
        return databarang.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private final TextView txt_namaaset;
        private final TextView txt_hargaaset;
        private final TextView txt_asetid;
        private final TextView jumlah_asset;
        private final RelativeLayout relativeLayout;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.id_aset);
            txt_namaaset = itemView.findViewById(R.id.nama_aset);
            txt_hargaaset = itemView.findViewById(R.id.harga_aset);
            txt_asetid = itemView.findViewById(R.id.aset_id);
            jumlah_asset = itemView.findViewById(R.id.jumlah_asset);
        }
    }
}
