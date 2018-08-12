package com.gamora.gamoraapp.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

;
import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.PlatformManager;
import com.gamora.gamoraapp.model.data.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class AdapterFeed extends ArrayAdapter<Post> {

    private  Context nContext;
    private int nResource;
    private File postFirebaseImage;
    private MyViewHolder mvh;
    private Boolean booYa = false;
    private DatabaseReference databaseReference;

    private Post postData;

    public AdapterFeed(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        nContext = context;
        nResource = resource;
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
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
        mvh.booYaBtnText = (TextView) convertView.findViewById(R.id.booya_btn_text);

        mvh.userNicknameText.setText(post.getUserID());
        mvh.postDateText.setText(post.getPostDate().toString());
        mvh.booyaCountText.setText(String.valueOf(post.getBooyaCount()));
        mvh.postDescriptionText.setText(post.getContent());
        mvh.postGameText.setText(post.getGame());
        mvh.postPlatformText.setText(PlatformManager.getInstance().getPlatformsStrings().get(post.getPlatformID()));
        //downloadPostToImageView(postID, convertView);
        mvh.postImage.setOnClickListener(view -> {
            downloadPostToImageView(postID, mvh.postImage);
        });
        downloadProfilePicToImageView(userID, convertView);
        mvh.booYaBtnText.setOnClickListener(view -> {
            if(!booYa) {
                post.setBooyaCount(post.getBooyaCount() + 1);
                Query query = databaseReference.child("uid").equalTo(postID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.toString());
                        if(dataSnapshot.exists()) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                postData = snapshot.getValue(Post.class);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_LONG).show();
                    }
                });
                postData.setBooyaCount(postData.getBooyaCount() + 1);
                databaseReference.child(postID).setValue(postData);
                mvh.booyaCountText.setText(postData.getBooyaCount());
                booYa = true;
            }
        });
        convertView.setTag(mvh);

        return convertView;
    }

    private void downloadProfilePicToImageView(String userID, View convert) {
        StorageReference profilePicImage = FirebaseStorage.getInstance().getReference().child("profile_pics");
        try {
            postFirebaseImage = File.createTempFile("images", null, convert.getContext().getCacheDir());
        }catch (Exception e){

        }
        profilePicImage.getFile(postFirebaseImage).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                mvh.postImage.setImageURI(Uri.fromFile(postFirebaseImage));
            }
        });
    }

    private void downloadPostToImageView(String postID, View convert) {
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
        TextView userNicknameText, postDateText, postDescriptionText, booyaCountText, postGameText, postPlatformText, booYaBtnText;
        ImageView postImage, userProfilePic;
        public MyViewHolder() {
        }

    }

}
