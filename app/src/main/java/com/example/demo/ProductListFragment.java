package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProductListFragment extends Fragment {
    private FirebaseAuth auth;
    private Button log_out_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabs_store, container, false);
        log_out_button = root.findViewById(R.id.log_out_button);

        log_out_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut();
                    }
                }
        );
        auth = FirebaseAuth.getInstance();


        return root;
    }

    private void logOut(){
        auth.signOut();
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}
