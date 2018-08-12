package com.gamora.gamoraapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.PlatformManager;
import com.gamora.gamoraapp.model.data.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

public class AdapterFeed extends ArrayAdapter<Post> {

    private  Context nContext;
    private int nResource;
    private FirebaseStorage storageDisplayImg;
    private File postFirebaseImage;
    private MyViewHolder mvh;

    public AdapterFeed(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        nContext=context;
        nResource=resource;
        storageDisplayImg = FirebaseStorage.getInstance();
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String postID = getItem(position).getPostID();
        String userID = getItem(position).getUserID();
        String postContent = getItem(position).getContent();
        String postGame = getItem(position).getGame();
        int platformID = getItem(position).getPlatformID();
        int postBooyas = getItem(position).getBooyaCount();

        Post post = new Post(postID, userID, platformID, postGame, postContent, new Date(), "123456", postBooyas);
        LayoutInflater inflater = LayoutInflater.from(nContext);
        mvh = new MyViewHolder();
        convertView = inflater.inflate(nResource, parent, false);


        mvh.userProfilePic = (ImageView) convertView.findViewById(R.id.user_profile_pic);
        mvh.postImage = (ImageView) convertView.findViewById(R.id.post_image);
        mvh.userNicknameText = (TextView) convertView.findViewById(R.id.user_nickname);
        mvh.postDateText = (TextView) convertView.findViewById(R.id.post_date);
        mvh.postDescriptionText = (TextView) convertView.findViewById(R.id.post_description);
        mvh.booyaCountText = (TextView) convertView.findViewById(R.id.booya_given_count);
        mvh.postGameText = (TextView) convertView.findViewById(R.id.post_game);
        mvh.postPlatformText = (TextView) convertView.findViewById(R.id.post_platform);

        mvh.userNicknameText.setText(post.getUserID());
        mvh.postDateText.setText(post.getPostDate().toString());
        mvh.booyaCountText.setText(String.valueOf(post.getBooyaCount()));
        mvh.postDescriptionText.setText(post.getContent());
        mvh.postGameText.setText(post.getGame());
        mvh.postPlatformText.setText(PlatformManager.getInstance().getPlatformsStrings().get(post.getPlatformID()));
        mvh.postImage.setOnClickListener(view -> {
            downloadPostToImageView(postID, mvh.postImage);
//            Glide.with(convertView.getContext()).load(new FirebaseImageLoader()).load(storageReference)
//                    .into(mvh.postImage);
        });

        convertView.setTag(mvh);

        return convertView;
    }


    private void downloadPostToImageView(String postID, View convert) {
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Downloading...");
//        progressDialog.show();
//        storageReference = FirebaseStorage.getInstance().getReference().child("posts").child("images").child(postID);
//        Toast.makeText(getContext(), storageReference.toString(), Toast.LENGTH_LONG).show();
//        try {
//            postFirebaseImage = File.createTempFile("posts", null, getContext().getCacheDir());
//        } catch (Exception e){
//            Toast.makeText(getContext(), "Error loaging post", Toast.LENGTH_SHORT).show();
//        }
//        storageReference.getFile(postFirebaseImage.getAbsoluteFile()).addOnSuccessListener(taskSnapshot -> {
//            progressDialog.dismiss();
//            Toast.makeText(getContext(), "Downloaded", Toast.LENGTH_LONG).show();
//        }).addOnFailureListener(e -> {
//            progressDialog.dismiss();
//            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }).addOnProgressListener(taskSnapshot -> {
//            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//            progressDialog.setMessage("Downloaded " + (int) progress + "%");
//        });
//        glide.load(BitmapFactory.decodeFile(postFirebaseImage.getAbsolutePath())).into(iv);
        //iv.setImageBitmap(BitmapFactory.decodeFile(postFirebaseImage.getPath()));

        StorageReference postImage = FirebaseStorage.getInstance().getReference().child("posts").child("images").child(postID);
        try {
            postFirebaseImage = File.createTempFile("images", null, convert.getContext().getCacheDir());
        }catch (Exception e){

        }
        postImage.getFile(postFirebaseImage).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                mvh.postImage.setImageURI(Uri.fromFile(postFirebaseImage));
            }
        });
    }


    public static class MyViewHolder {
        TextView userNicknameText, postDateText, postDescriptionText, booyaCountText, postGameText, postPlatformText;
        ImageView postImage, userProfilePic;
        Layout booyaBtn;
        public MyViewHolder() {
        }

    }

}
