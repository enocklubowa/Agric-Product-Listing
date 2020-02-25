package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.demo.ViewHolder.ProductAdapter;
import com.example.demo.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private View view;
    private LinearLayout logout_button;
    private LoaderTextView name_field, email_field, phone_field;
    private ProgressBar loading_products;
    private RecyclerView recyclerView;
    private Query reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name_field = root.findViewById(R.id.name_field);
        email_field = root.findViewById(R.id.email_field);
        phone_field = root.findViewById(R.id.phone_field);
        logout_button = root.findViewById(R.id.log_out_button);
        loading_products = root.findViewById(R.id.loading_products);
        recyclerView = root.findViewById(R.id.recyclerView);
        reference = FirebaseDatabase.getInstance().getReference().child("products").orderByChild("userId").equalTo(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        //recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        getUserInfo();
        populateProducts();
        logout_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }
        );
        return root;
    }

    private void getUserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user!=null){
                            name_field.setText(user.getName());
                            email_field.setText(user.getEmail());
                            phone_field.setText(user.getPhone());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void populateProducts(){

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference, Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, ProductAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductAdapter>(
                options
        ) {
            @Override
            protected void onBindViewHolder(@NonNull ProductAdapter holder, int position, @NonNull Product model) {
                Log.e("Binded view holder", "Binded view");

                holder.setDetails(model.getName(), model.getLocation(), model.getPrice());
            }

            @NonNull
            @Override
            public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.e("created view holder", "created view");
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_product, parent, false);
                loading_products.setVisibility(View.GONE);

                return new ProductAdapter(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
}
