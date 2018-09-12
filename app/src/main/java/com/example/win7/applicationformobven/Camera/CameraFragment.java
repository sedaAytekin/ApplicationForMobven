package com.example.win7.applicationformobven.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.apache.commons.io.FileUtils;
import com.example.win7.applicationformobven.R;
import com.example.win7.applicationformobven.databinding.CameraFragmentBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class CameraFragment extends Fragment {
    CameraFragmentBinding binding;
    public static int REQUEST_GALLERY = 10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, null, false);


        binding.takePhotoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraUpload();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null){
            createImageBody(data);
        }

    }

    private void openCameraUpload() {

        if (getContext() != null) {
            boolean hasAllPermissions = (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
            if (!hasAllPermissions) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Dexter.withActivity(getActivity()).withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    if (report.areAllPermissionsGranted()) {
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, REQUEST_GALLERY);
                                    } else {
                                        Snackbar.make(binding.getRoot(), "Kamera izni gerekli", 3000)
                                                .setAction(R.string.settings, v -> {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                                                    intent.setData(uri);
                                                    startActivity(intent);
                                                }).show();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }


                            }).check();
                }
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
        }
    }
    public void createImageBody(Intent data){

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        if (picturePath != null && !picturePath.equals("")) {
            //image upload ederken multipart kullanılıyor.
            File file = FileUtils.getFile(picturePath);

            loadImageFromFile(getContext(), file);
        }

    }
    //image i basıyoruz.
    private void loadImageFromFile(Context context, File f){
        Picasso.with(context).load(f).centerCrop().fit().into(binding.putImageIV);
    }
}
