package com.example.pdf_view_micro_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfViewHolder> {

    private Context context;
    private List<File> pdfFiles;
    private OnPdfFileSelectListener listener;
    // Assuming your PdfAdapter has a method to set the data
    public void setFiles(List<File> newData) {
        pdfFiles.clear(); // Clear the existing data
        pdfFiles.addAll(newData); // Add the new data
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }



    public PdfAdapter(Context context, List<File> pdfFiles,OnPdfFileSelectListener listener)
    {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;

    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PdfViewHolder(LayoutInflater.from(context).inflate(R.layout.element_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        holder.tvName.setText(pdfFiles.get(position).getName());
        holder.tvName.setSelected(true);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPdfSelected(pdfFiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
