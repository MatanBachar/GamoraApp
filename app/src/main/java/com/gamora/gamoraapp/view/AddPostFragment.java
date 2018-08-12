package com.gamora.gamoraapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.PlatformManager;
import com.gamora.gamoraapp.model.data.Post;
import com.gamora.gamoraapp.model.data.User;
import com.gamora.gamoraapp.view.Utils.CodesContainer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class AddPostFragment extends Fragment {

    //Firebase Objects
    DatabaseReference usersReference;
    DatabaseReference postsDatabase;
    FirebaseStorage mStorage;
    StorageReference postsStorageRef;

    BaseMainActivity activityRef;

    User userData;
    Post newPost;

    //Widgets
    Button uploadBtn;
    Button galleryBtn;
    Button cameraBtn;

    //Platform radios
    RadioButton radioPS4;
    RadioButton radioPC;
    RadioButton radioXboxOne;
    RadioButton radioNintendoSwitch;
    int choice;

    ImageView imageToUpload;
    EditText postDescription;
    EditText postGame;

    private Uri filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseMain = inflater.inflate(R.layout.fragment_addpost, container, false);
        activityRef = ((BaseMainActivity)getActivity());

        //Firebase Init
        mStorage = FirebaseStorage.getInstance();
        postsStorageRef = mStorage.getReference();
        usersReference = FirebaseDatabase.getInstance().getReference("users");
        postsDatabase = FirebaseDatabase.getInstance().getReference("posts");

//        Query query = usersReference.orderByChild("uid").equalTo("5aXfMmO79yTA27EzP8VzXVXUNQh2");
//        query.addListenerForSingleValueEvent(activityRef.valueEventListener);

        //Init view
        postGame = (EditText) baseMain.findViewById(R.id.post_game);
        postDescription = (EditText) baseMain.findViewById(R.id.post_description);
        galleryBtn = (Button) baseMain.findViewById(R.id.open_gallery_btn);
        uploadBtn = (Button) baseMain.findViewById(R.id.post_upload_btn);
        cameraBtn = (Button) baseMain.findViewById(R.id.open_camera_btn);
        imageToUpload = (ImageView) baseMain.findViewById(R.id.post_image);
        radioPS4 = (RadioButton) baseMain.findViewById(R.id.ps4_radio);
        radioXboxOne = (RadioButton) baseMain.findViewById(R.id.xbox_one_radio);
        radioPC = (RadioButton) baseMain.findViewById(R.id.pc_radio);
        radioNintendoSwitch = (RadioButton) baseMain.findViewById(R.id.nintendo_switch_radio);


        //Set listeners
        galleryBtn.setOnClickListener(view -> chooseImage());
        cameraBtn.setOnClickListener(view -> captureImage());
        uploadBtn.setOnClickListener(view -> {
            if((!radioXboxOne.isChecked() && !radioPC.isChecked() && !radioNintendoSwitch.isChecked() && !radioPS4.isChecked())
                    || postGame.getEditableText().toString().equals("") || postGame.getEditableText().toString().equals("")) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Error");
                progressDialog.setMessage("Please fill all the details to upload your post!");
                progressDialog.show();
            }
            uploadImage();
        });
        radioPS4.setOnClickListener(radioListener);
        radioNintendoSwitch.setOnClickListener(radioListener);
        radioXboxOne.setOnClickListener(radioListener);
        radioPC.setOnClickListener(radioListener);
        return baseMain;
    }


    android.view.View.OnClickListener radioListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();
            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.ps4_radio:
                    if (checked)
                        choice = PlatformManager.getInstance().getPlatformsStrings().indexOfValue("Playstation 4");
                    break;
                case R.id.xbox_one_radio:
                    if (checked)
                        choice = PlatformManager.getInstance().getPlatformsStrings().indexOfValue("XBOX One");
                    break;
                case R.id.nintendo_switch_radio:
                    if (checked)
                        choice = PlatformManager.getInstance().getPlatformsStrings().indexOfValue("Nintendo Switch");
                    break;
                case R.id.pc_radio:
                    if (checked)
                        choice = PlatformManager.getInstance().getPlatformsStrings().indexOfValue("PC");
                    break;
            }
        }
    };

    private void captureImage() {
        if (checkSelfPermission(getContext() ,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    CodesContainer.MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CodesContainer.CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CodesContainer.MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CodesContainer.CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String postID = UUID.randomUUID().toString();
            String storageUri = "posts/images/" + postID;
            newPost = new Post(postID, activityRef.getCurrentUser().getUid(), choice, postGame.getEditableText().toString(), postDescription.getEditableText().toString(), new Date(), storageUri, 0);
            if(activityRef.getUserData().getPosts() == null) {
                List<String> firstPost = new ArrayList<>();
                firstPost.add(postID);
                activityRef.getUserData().setPosts(firstPost);
            } else {
                activityRef.getUserData().getPosts().add(postID);
            }


            postsDatabase.child(postID).setValue(newPost);
            StorageReference ref = postsStorageRef.child(storageUri);
            ref.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
            }).addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress + "%");
            });
        }
    }

    private void chooseImage() {
        if (Build.VERSION.SDK_INT <19){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_image)), CodesContainer.GALLERY_INTENT_CALLED);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, CodesContainer.GALLERY_KITKAT_INTENT_CALLED);
        }
    }
//
    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || null == data) return;
        if (requestCode == CodesContainer.CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageToUpload.setImageBitmap(photo);
        }
        if (requestCode == CodesContainer.GALLERY_INTENT_CALLED) {
            filePath = data.getData();
        } else if (requestCode == CodesContainer.GALLERY_KITKAT_INTENT_CALLED) {
            filePath = data.getData();
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // Check for the freshest data.
            getContext().getContentResolver().takePersistableUriPermission(filePath, takeFlags);
        }

        //Importing image using URI
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
            imageToUpload.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Problem importing image", Toast.LENGTH_LONG).show();
        }
    }
}
