    package com.example.pdf_view_micro_project;

    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.annotation.NonNull;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Environment;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import androidx.appcompat.widget.SearchView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.karumi.dexter.Dexter;
    import com.karumi.dexter.PermissionToken;
    import com.karumi.dexter.listener.PermissionDeniedResponse;
    import com.karumi.dexter.listener.PermissionGrantedResponse;
    import com.karumi.dexter.listener.PermissionRequest;
    import com.karumi.dexter.listener.single.PermissionListener;
    import android.Manifest;
    import java.io.File;
    import java.util.ArrayList;
    import java.util.List;

    public class MainActivity extends AppCompatActivity implements OnPdfFileSelectListener {
        private TextView noPdfText,no_pdf_text_folder;
        private PdfAdapter pdfAdapter;
        private List<File> pdfList;
        private RecyclerView recyclerView;
        public Button prmsnBtn;
        private SearchView search_bar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            setContentView(R.layout.activity_main);
            recyclerView = findViewById(R.id.recycler_view);
            pdfList = new ArrayList<>();
            pdfAdapter = new PdfAdapter(this, pdfList,this);
            noPdfText = findViewById(R.id.no_pdf_text);
            no_pdf_text_folder = findViewById(R.id.no_pdf_text_folder);
            prmsnBtn  = findViewById(R.id.prmsnBtn);
            runtimePermission();
        }



        private void runtimePermission() {
            Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                    Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    displayPdf();
                    prmsnBtn.setVisibility(View.GONE);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                    Toast.makeText(MainActivity.this, "Permission is Required", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    prmsnBtn.setVisibility(View.VISIBLE);
                    noPdfText.setVisibility(View.VISIBLE);
                    no_pdf_text_folder.setVisibility(View.VISIBLE);
                    prmsnBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            runtimePermission();
                        }
                    });
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
        }


        public ArrayList<File> findPdf (File file)
        {
            ArrayList<File> arrayList = new ArrayList<>();
            File[] files = file.listFiles();


            for(File singleFile: files)
            {
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(findPdf(singleFile));

                }
                else
                {
                    if(singleFile.getName().endsWith(".pdf")){
                        arrayList.add(singleFile);

                    }
                }
            }
            return arrayList;
        }


        private void displayPdf() {
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            pdfList = new ArrayList<>();

            pdfList.addAll(findPdf(Environment.getExternalStorageDirectory()));

            if (pdfList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                noPdfText.setVisibility(View.VISIBLE);
                no_pdf_text_folder.setVisibility(View.VISIBLE);

            } else {
                noPdfText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                no_pdf_text_folder.setVisibility(View.GONE);
                pdfAdapter = new PdfAdapter(this, pdfList,this);
                recyclerView.setAdapter(pdfAdapter);
                pdfAdapter.notifyDataSetChanged();
            }
        }


        @Override
        public void onPdfSelected(File file) {
            startActivity(new Intent(MainActivity.this, DocumentActivity.class)
                    .putExtra("path",file.getAbsolutePath()));
        }
    }