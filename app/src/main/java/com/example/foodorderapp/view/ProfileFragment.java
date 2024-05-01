package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        RoundedImageView nextToUpdateUser = rootView.findViewById(R.id.next_to_user_detail);
        nextToUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUpdateFragment userUpdateFragment = new UserUpdateFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.next_to_user_detail, userUpdateFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button logoutButton = rootView.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance().clearUserId();
                Intent intent = new Intent(rootView.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
}
