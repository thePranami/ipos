package com.loopin.ipos.Fragment;

import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.loopin.ipos.BuildConfig;
import com.loopin.ipos.R;

import java.util.Objects;

public class InfoFragment extends DialogFragment implements View.OnClickListener{

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_fragment, container, false);
        TextView ok = view.findViewById(R.id.ok);
        ImageView netWorkImg = view.findViewById(R.id.netWorkImg);
        Glide.with(Objects.requireNonNull(getContext())).load(Uri.parse("https://pbs.twimg.com/profile_images/700324627155853312/xJYcZDj0_400x400.png")).into(netWorkImg);
        //netWorkImg.setImageURI(Uri.parse("https://pbs.twimg.com/profile_images/700324627155853312/xJYcZDj0_400x400.png"));
        ok.setOnClickListener(this);
        TextView visitWeb = view.findViewById(R.id.visitWeb);
        visitWeb.setOnClickListener(this);
        TextView mailUs = view.findViewById(R.id.mailUs);
        mailUs.setOnClickListener(this);
        TextView devName = view.findViewById(R.id.devName);
        devName.setOnClickListener(this);
        TextView appVersion = view.findViewById(R.id.appVersion);
        appVersion.setText("\u25AA "+BuildConfig.VERSION_NAME);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.devName:
                Intent dIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pbs.twimg.com/profile_images/700324627155853312/xJYcZDj0_400x400.png"));
                startActivity(dIntent);
                break;
            case R.id.ok:
                dismiss();
                break;
            case R.id.visitWeb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://loopintechies.com/"));
                startActivity(browserIntent);
                break;
            case R.id.mailUs:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+"mail.loopintechies@gmail.com"));
                startActivity(intent);
                break;
        }
    }
}
