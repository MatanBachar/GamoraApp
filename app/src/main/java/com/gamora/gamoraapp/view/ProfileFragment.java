package com.gamora.gamoraapp.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.view.Utils.CodesContainer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    //Firebase objects
    DatabaseReference usersReference;
    FirebaseAuth mAuth;
    StorageReference profilePics;

    //Views
    CircleImageView profilePicView;
    Button signOutBtn;
    TextView nicknameText;
    BaseMainActivity activityRef;

    private File postFirebaseImage;

    Uri filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseMain = inflater.inflate(R.layout.fragment_profile, container, false);

        //Firebase init
        mAuth = FirebaseAuth.getInstance();
        profilePics = FirebaseStorage.getInstance().getReference();
        usersReference = FirebaseDatabase.getInstance().getReference("users");

        //VIews init
        profilePicView = (CircleImageView) baseMain.findViewById(R.id.profile_pic_view);
        signOutBtn = (Button) baseMain.findViewById(R.id.sign_out_btn);
        nicknameText = (TextView) baseMain.findViewById(R.id.nickname_text);
        activityRef = ((BaseMainActivity)getActivity());


        signOutBtn.setOnClickListener(view -> signOut());
        profilePicView.setOnClickListener(view -> {
            DialogInterface.OnClickListener dialogClickListener = (DialogInterface dialog, int which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE: uploadProfilePic();break;
                    case DialogInterface.BUTTON_NEGATIVE: dialog.cancel(); break; } };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Change your profile picture?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        StorageReference profilePic = FirebaseStorage.getInstance().getReference().child("profile_pics").child("images");
        try {
            postFirebaseImage = File.createTempFile("images", null, getContext().getCacheDir());
        }catch (Exception e){

        }
        profilePic.getFile(postFirebaseImage).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                profilePicView.setImageURI(Uri.fromFile(postFirebaseImage));
            }
        });
        return baseMain;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void uploadProfilePic() {
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

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || null == data) return;
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
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        String storageUri = "profile_pics/" + activityRef.getUserData().getUID();
        StorageReference ref = profilePics.child(storageUri);
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

        //Importing image using URI
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
            profilePicView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Problem importing image", Toast.LENGTH_LONG).show();
        }
    }

    public void signOut(){
        mAuth.signOut();
        startActivity(new Intent(getActivity(), LogInActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            String nickname = activityRef.getUserData().getRealName() + "\n\"" + activityRef.getUserData().getNickname() + "\"";
            nicknameText.setText(nickname);
        } catch (Exception e){

        }
    }
}
