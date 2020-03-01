package com.example.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.ViewHolder.ProductAdapter;
import com.example.demo.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductListFragment extends Fragment {
    private FirebaseAuth auth;
    private Button log_out_button;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseReference reference;
    private ProgressBar loading_products;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("products");

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        loading_products = (ProgressBar) root.findViewById(R.id.loading_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 8), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        populateProducts();
        return root;
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
                holder.setDetails(model.getName(), model.getLocation(), model.getPrice());
            }

            @NonNull
            @Override
            public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
